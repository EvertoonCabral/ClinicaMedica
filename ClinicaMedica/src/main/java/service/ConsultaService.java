package service;

import models.Consulta;
import models.Medico;
import models.Paciente;
import org.springframework.stereotype.Service;
import repository.ConsultaRepository;
import repository.MedicoRepository;
import service.MedicoService;
import service.PacienteService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConsultaService {
    private ConsultaRepository consultaRepository;
    private PacienteService pacienteService;
    private MedicoService medicoService;

    private MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteService pacienteService, MedicoService medicoService) {
        this.consultaRepository = consultaRepository;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }


    public Consulta findById(Long id) throws Exception {
        Optional<Consulta> retorno = consultaRepository.findById(id);

        if (retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Consulta com ID " + id + " Não Identificado");
    }

    public List<Consulta> findAll() {
        return consultaRepository.findAll();
    }

    private boolean isHorarioFuncionamentoValido(LocalDateTime dataHora) {
        LocalTime horarioAbertura = LocalTime.of(7, 0); // Horário de abertura da clínica
        LocalTime horarioFechamento = LocalTime.of(19, 0); // Horário de fechamento da clínica

        LocalTime horaConsulta = dataHora.toLocalTime();

        return horaConsulta.isAfter(horarioAbertura) && horaConsulta.isBefore(horarioFechamento);
    }

    private boolean isAntecedenciaMinimaValida(LocalDateTime dataHora) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioMinimoAgendamento = agora.plusMinutes(30); // Antecedência mínima de 30 minutos

        return dataHora.isAfter(horarioMinimoAgendamento);
    }
    public Consulta agendarConsulta(Consulta consulta) {
        try {

            Paciente paciente = consulta.getPaciente();
            if (paciente == null || !paciente.isAtivo()) {
                throw new IllegalArgumentException("Paciente inativo ou não encontrado");
            }


            Medico medico;
            Long idMedico = consulta.getMedico().getId();
            if (idMedico != null) {
                medico = medicoService.findById(idMedico);
                if (medico == null || !medico.isAtivo()) {
                    throw new IllegalArgumentException("Médico inativo ou não encontrado");
                }
            } else {

                medico = findRandomMedicoDisponivel(consulta.getDataHora());
                if (medico == null) {
                    throw new IllegalArgumentException("Não há médicos disponíveis para a data/hora informada");
                }
            }

            if (!isHorarioFuncionamentoValido(consulta.getDataHora())) {
                throw new IllegalArgumentException("Horário de funcionamento da clínica é das 07:00 às 19:00");
            }
            if (!isAntecedenciaMinimaValida(consulta.getDataHora())) {
                throw new IllegalArgumentException("Deve-se agendar as consultas com 30 minutos de antecedência");
            }
            if (consultaRepository.existsByPacienteAndDataHoraBetween(paciente, consulta.getDataHora().toLocalDate().atStartOfDay(), consulta.getDataHora().toLocalDate().atTime(LocalTime.MAX))) {
                throw new IllegalArgumentException("Você já tem uma consulta marcada para esse dia");
            }

            if (consultaRepository.existsByMedicoAndDataHora(medico, consulta.getDataHora())) {
                throw new IllegalArgumentException("Médico já possui uma consulta nesse dia ou horário");
            }

            consulta.setPaciente(paciente);
            consulta.setMedico(medico);

            consulta = consultaRepository.save(consulta);

            return consulta;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage(), e);
        }
    }

    public Medico findRandomMedicoDisponivel(LocalDateTime dataHora) {

        List<Medico> medicosAtivos = medicoRepository.findByAtivoTrue();

        List<Medico> medicosDisponiveis = new ArrayList<>();
        for (Medico medico : medicosAtivos) {
            boolean disponivel = !consultaRepository.existsByMedicoAndDataHora(medico, dataHora);
            if (disponivel) {
                medicosDisponiveis.add(medico);
            }
        }

        if (medicosDisponiveis.isEmpty()) {
            return null;
        }
        // Selecionar aleatoriamente um médico disponível
        Random random = new Random();
        int index = random.nextInt(medicosDisponiveis.size());
        return medicosDisponiveis.get(index);
    }

    public void cancelarConsulta(Long idConsulta, String motivoCancelamento) {
        Consulta consulta = consultaRepository.findById(idConsulta).orElse(null);

        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        if (consulta.isCancelada()) {
            throw new IllegalArgumentException("A consulta já foi cancelada anteriormente");
        }

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        LocalDateTime limiteCancelamento = dataHoraAtual.plusHours(24);

        if (consulta.getDataHora().isBefore(limiteCancelamento)) {
            throw new IllegalArgumentException("A consulta só pode ser cancelada com antecedência mínima de 24 horas");
        }

        consulta.setCancelada(true);
        consulta.setMotivoCancelamento(motivoCancelamento);

        consultaRepository.save(consulta);
    }

}

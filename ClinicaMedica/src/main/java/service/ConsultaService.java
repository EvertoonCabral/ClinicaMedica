package service;

import models.Consulta;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ConsultaService {
    private ConsultaRepository consultaRepository;
    private PacienteService pacienteService;
    private MedicoService medicoService;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteService pacienteService, MedicoService medicoService) {
        this.consultaRepository = consultaRepository;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
    }

    public Consulta agendarConsulta(Consulta consulta) {
        // Verificar se o paciente está ativo no sistema
        if (!pacienteService.isPacienteAtivo(consulta.getPaciente())) {
            throw new RuntimeException("Não é possível agendar consulta com paciente inativo");
        }

        // Verificar se o médico está ativo no sistema
        if (!medicoService.isMedicoAtivo(consulta.getMedico())) {
            throw new RuntimeException("Não é possível agendar consulta com médico inativo");
        }

        // Verificar se já existe uma consulta agendada para o paciente na mesma data/hora
        if (consultaRepository.existsByPacienteAndDataHora(consulta.getPaciente(), consulta.getDataHora())) {
            throw new RuntimeException("Já existe uma consulta agendada para o paciente na mesma data/hora");
        }

        // Verificar se o médico já possui uma consulta agendada na mesma data/hora
        if (consultaRepository.existsByMedicoAndDataHora(consulta.getMedico(), consulta.getDataHora())) {
            throw new RuntimeException("O médico já possui uma consulta agendada na mesma data/hora");
        }

        // Verificar se a data/hora da consulta está dentro do horário de funcionamento da clínica
        if (!isHorarioFuncionamentoValido(consulta.getDataHora())) {
            throw new RuntimeException("A data/hora da consulta está fora do horário de funcionamento da clínica");
        }

        // Verificar se a consulta foi agendada com antecedência mínima de 30 minutos
        if (!isAntecedenciaMinimaValida(consulta.getDataHora())) {
            throw new RuntimeException("A consulta deve ser agendada com antecedência mínima de 30 minutos");
        }

        // Realizar o agendamento da consulta
        Consulta consultaAgendada = consultaRepository.save(consulta);
        return consultaAgendada;
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
}

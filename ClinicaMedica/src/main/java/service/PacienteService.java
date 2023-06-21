package service;

import br.unipar.clinicamedica.projection.PacienteProjection;
import models.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import repository.PacienteRepository;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente insert(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente edit(Paciente paciente) throws Exception {
        Paciente original = pacienteRepository.findById(paciente.getId())
                .orElseThrow(() -> new Exception("Paciente não encontrado!!!!!"));

        original.setNome(paciente.getNome());
        original.setEmail(paciente.getEmail());
        original.setTelefone(paciente.getTelefone());
        original.setCpf(paciente.getCpf());
        original.setEndereco(paciente.getEndereco());

        return pacienteRepository.save(original);
    }

    public List<Paciente> listarPacientesOrdenadosPorNome() {
        return pacienteRepository.findAllByOrderByNomeAsc();
    }

    public Paciente findById(Long id) throws Exception {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Paciente não encontrado"));
    }
    public void excluir(Long id) throws Exception {
        // Busca o paciente original no banco de dados
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Paciente não encontrado"));

        paciente.setAtivo(false);
        pacienteRepository.saveAndFlush(paciente);
    }

    public Paciente update (Paciente paciente) throws Exception {
        Paciente original = buscarPacientePorId(paciente.getId());

        validarAlteracaoEmail(original, paciente);
        validarAlteracaoCPF(original, paciente);

        original.setNome(paciente.getNome());
        original.setTelefone(paciente.getTelefone());
        original.setEndereco(paciente.getEndereco());

        return pacienteRepository.save(original);
    }

    private Paciente buscarPacientePorId(Long id) throws Exception {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Paciente não encontrado"));
    }

    private void validarAlteracaoEmail(Paciente original, Paciente atualizado) throws Exception {
        if (!atualizado.getEmail().equals(original.getEmail())) {
            throw new Exception("Não é permitido alterar o e-mail do paciente");
        }
    }

    private void validarAlteracaoCPF(Paciente original, Paciente atualizado) throws Exception {
        if (!atualizado.getCpf().equals(original.getCpf())) {
            throw new Exception("Não é permitido alterar o CPF do paciente");
        }
    }


}


package service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import models.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MedicoRepository;



@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico insert(Medico medico) throws Exception {

        validarInsercao(medico);

        medicoRepository.saveAndFlush(medico);

        return medico;

    }

    public Medico edit(Long id, Medico medico) throws Exception {

        validarEdicao(medico);

        validarEdicao(medico);

        // Busca o médico original no banco de dados
        Medico original = medicoRepository.findById(id)
                .orElseThrow(() -> new Exception("Médico não encontrado"));

        // Atualiza os campos permitidos
        original.setNome(medico.getNome());
        original.setTelefone(medico.getTelefone());
        original.setEndereco(medico.getEndereco());


        return medicoRepository.saveAndFlush(original);

    }

    public void excluir (Long id) throws Exception {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new Exception("Médico não encontrado"));


        medico.setAtivo(false);


        medicoRepository.save(medico);
    }


    public List<Medico> findAll() {

        return medicoRepository.findAll();

    }

    public List<Medico> findByFilters(String nome) {

        return medicoRepository.findByNomeContainingAllIgnoringCase(nome);

    }


    public List<Medico> findAllMedicosNomeAsc (){ //Requesito de listagem de medico pelo nome Crescente

        List<Medico> medicos = medicoRepository.findAllByOrderByNomeAsc();
        medicos.sort(Comparator.comparing(Medico::getNome));
        return medicos;
    }



    public Medico findById(Long id) throws Exception {

        Optional<Medico> retorno = medicoRepository.findById(id);

        if (retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Medico com Id "+id+" Não Identificada");

    }

    private void validarInsercao(Medico medico) throws Exception {

        if (medico.getId() != 0) {
            throw new Exception("Para operação de inserção não deve se enviar o ID.");
        }

        validarMedico(medico);

    }

    private void validarEdicao(Medico medico) throws Exception {

        if (medico.getId() == 0) {
            throw new Exception("Para operação de edição deve se enviar o ID.");
        }

        validarMedico(medico);

    }

    private void validarMedico(Medico medico) throws Exception {
        // Busca o médico original no banco de dados
        Medico original = medicoRepository.findById(medico.getId())
                .orElseThrow(() -> new Exception("Id invalido, Médico não encontrado"));

       //Verificações
        if (!medico.getEmail().equals(original.getEmail())) {
            throw new Exception("Não é permitido alterar o e-mail do médico");
        }
        if (!medico.getCrm().equals(original.getCrm())) {
            throw new Exception("Não é permitido alterar o CRM do médico");
        }
        if (!medico.getEspecialidade().equals(original.getEspecialidade())) {
            throw new Exception("Não é permitido alterar a especialidade do médico!!!");
        }
    }

}


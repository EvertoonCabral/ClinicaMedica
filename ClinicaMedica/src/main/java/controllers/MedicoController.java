package controllers;

import java.util.List;


import models.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.MedicoService;

@RestController
@RequestMapping(path = "/marca")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public Medico insert(@RequestBody Medico medico) throws Exception {

        return medicoService.insert(medico);

    }


    @GetMapping
    public List<Medico> findAll() throws Exception {

        return medicoService.findAll();

    }

    @GetMapping(path = "/{id}")
    public Medico findById(@PathVariable Long id) throws Exception {

        return medicoService.findById(id);

    }

    @GetMapping(path = "/filter")
    public List<Medico> findByFilters(@RequestParam("nome") String nome) throws Exception {

        return medicoService.findByFilters(nome);

    }

    @GetMapping("/orderedByName")
    public List<Medico> findAllMedicosOrderedByName() {

        return medicoService.findAllMedicosNomeAsc();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirMedico(@PathVariable Long id) {
        try {
            medicoService.excluir(id);
            return ResponseEntity.ok("Médico excluido com sucesso"); //Se der certo retorna um 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Deu pau retorna 400
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Medico> atualizarMedico(@PathVariable Long id, @RequestBody Medico medico) {
        try {
            Medico medicoAtualizado = medicoService.edit(id, medico);
            return ResponseEntity.ok(medicoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}


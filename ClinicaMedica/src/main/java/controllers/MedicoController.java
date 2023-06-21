package controllers;

import java.util.List;


import models.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.MedicoService;

@RestController
@RequestMapping(path = "/marca")
@Api(tags = "API Medico", description = "Medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    //@ApiOperation(value = "Adicionar um medico")
    public Medico insert(@RequestBody Medico medico) throws Exception {

        return medicoService.insert(medico);

    }


    @GetMapping
    //@ApiOperation(value = "Obter uma lista de medicos")
    public List<Medico> findAll() throws Exception {

        return medicoService.findAll();

    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Obter um medico pelo seu ID")
    public Medico findById(@PathVariable Long id) throws Exception {

        return medicoService.findById(id);

    }

    @GetMapping(path = "/filter")
    @ApiOperation(value = "Obter uma lista de medicos ordenados pelo nome")
    public List<Medico> findByFilters(@RequestParam("nome") String nome) throws Exception {

        return medicoService.findByFilters(nome);

    }

    @GetMapping("/orderedByName")
    @ApiOperation(value = "Obter uma lista de medicos ordenados pelo nome")
    public List<Medico> findAllMedicosOrderedByName() {

        return medicoService.findAllMedicosNomeAsc();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir um medico")
    public ResponseEntity<String> excluirMedico(@PathVariable Long id) {
        try {
            medicoService.excluir(id);
            return ResponseEntity.ok("Médico excluido com sucesso"); //Se der certo retorna um 200
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Deu pau retorna 400
        }
    }
    @PutMapping("/{id}")
    @ApiOperation( value = "Editar um medico")
    public ResponseEntity<Medico> atualizarMedico(@PathVariable Long id, @RequestBody Medico medico) {
        try {
            Medico medicoAtualizado = medicoService.edit(id, medico);
            return ResponseEntity.ok(medicoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}


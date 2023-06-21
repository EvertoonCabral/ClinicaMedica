package controllers;

import br.unipar.clinicamedica.projection.PacienteProjection;
import models.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import service.PacienteService;

import java.util.List;

@RestController
@RequestMapping(path = "/paciente")
@Api(tags = "API Paciente", description = "Paciente")
public class PacienteController {

@Autowired
private PacienteService pacienteService;

    @GetMapping
    @ApiOperation(value = "Obter uma lista de pacintes")
    public ResponseEntity<List<Paciente>> listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.listarPacientesOrdenadosPorNome();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Excluir um paciente")
    public ResponseEntity<String> excluirPaciente(@PathVariable Long id) {
        try {
            pacienteService.excluir(id);
            return ResponseEntity.ok("Paciente excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir paciente: " + e.getMessage());
        }
    }

        @PostMapping
         @ApiOperation(value = "Adicionar um paciente")
        public Paciente insert(@Valid @RequestBody Paciente paciente) throws Exception{
        return pacienteService.insert(paciente);
    }

    @PutMapping
   // @ApiOperation("Editar os dados de um paciente")
    public Paciente edit(@RequestBody @Valid Paciente paciente) throws Exception {
        return pacienteService.edit(paciente);
    }












}

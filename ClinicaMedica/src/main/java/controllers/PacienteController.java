package controllers;

import br.unipar.clinicamedica.projection.PacienteProjection;
import models.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service.PacienteService;

import java.util.List;

@Repository
public class PacienteController {
@Autowired
private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.listarPacientesOrdenadosPorNome();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPaciente(@PathVariable Long id) {
        try {
            pacienteService.excluir(id);
            return ResponseEntity.ok("Paciente excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir paciente: " + e.getMessage());
        }
    }










}

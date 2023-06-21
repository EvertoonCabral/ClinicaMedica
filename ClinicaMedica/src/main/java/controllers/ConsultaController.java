package controllers;

import models.Consulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ConsultaService;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping

    public ResponseEntity<?> agendarConsulta(@RequestBody Consulta consulta) {
        try {
            Consulta consultaAgendada = consultaService.agendarConsulta(consulta);
            return ResponseEntity.ok(consultaAgendada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @PostMapping("/{id}/cancelar")
        public ResponseEntity<?> cancelarConsulta(@PathVariable("id") Long idConsulta, @RequestBody String motivoCancelamento) {
            try {
                consultaService.cancelarConsulta(idConsulta, motivoCancelamento);
                return ResponseEntity.ok("Consulta cancelada com sucesso");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }



    @GetMapping
    @ApiOperation(value = "Obter uma lista de consultas")
    public List<Consulta> findAll(){

        return consultaService.findAll();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Obter uma consulta pelo seu ID")
    public Consulta findById(@PathVariable Long id) throws Exception {

        return consultaService.findById(id);
    }

    }


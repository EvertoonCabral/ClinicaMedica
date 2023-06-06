package controllers;

import models.Consulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<Consulta> agendarConsulta(@RequestBody Consulta consulta) {
        try {
            Consulta consultaAgendada = consultaService.agendarConsulta(consulta);
            return ResponseEntity.ok(consultaAgendada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

}

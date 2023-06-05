package controllers;

import java.util.List;


import models.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping
    public Medico edit(@RequestBody Medico medico) throws Exception {

        return medicoService.edit(medico);
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


}


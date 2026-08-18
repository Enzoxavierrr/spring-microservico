package br.pucrs.microdemo.microservico1.controller;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import br.pucrs.microdemo.microservico1.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Disciplina cadastrar(@RequestBody Disciplina disciplina) {
        return disciplinaService.cadastrar(disciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaService.listar();
    }
}

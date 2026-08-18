package br.pucrs.microdemo.microservico1.controller;

import java.net.URI;
import java.util.List;

import br.pucrs.microdemo.microservico1.domain.Estudante;
import br.pucrs.microdemo.microservico1.service.EstudanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstudanteController {
    private final EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @PostMapping("/estudantes")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody Estudante estudante) {
        return estudanteService.cadastrar(estudante)
                .map(salvo -> ResponseEntity.created(URI.create("/estudantes/" + salvo.getMatricula())).body(salvo))
                .orElse(ResponseEntity.status(409).build());
    }

    @GetMapping("/estudantes/{matricula}")
    public ResponseEntity<Estudante> consultarPorMatricula(@PathVariable String matricula) {
        return estudanteService.consultarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estudantes")
    public List<Estudante> consultarPorNome(@RequestParam("nome") String nome) {
        return estudanteService.consultarPorNome(nome);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarRequisicaoInvalida(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

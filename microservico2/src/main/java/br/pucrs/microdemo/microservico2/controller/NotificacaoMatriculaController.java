package br.pucrs.microdemo.microservico2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.microservico2.business.NotificacaoMatriculaService;
import br.pucrs.microdemo.microservico2.dto.NotificacaoMatriculaRequest;
import br.pucrs.microdemo.microservico2.dto.NotificacaoMatriculaResponse;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoMatriculaController {
    private final NotificacaoMatriculaService notificacaoMatriculaService;

    public NotificacaoMatriculaController(NotificacaoMatriculaService notificacaoMatriculaService) {
        this.notificacaoMatriculaService = notificacaoMatriculaService;
    }

    @PostMapping("/matricula")
    public NotificacaoMatriculaResponse gerarComprovante(@RequestBody NotificacaoMatriculaRequest request) {
        return notificacaoMatriculaService.gerarComprovante(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarRequisicaoInvalida(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

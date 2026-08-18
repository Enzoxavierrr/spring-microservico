package br.pucrs.microdemo.microservico2.dto;

public record NotificacaoMatriculaRequest(
        String nomeEstudante,
        String matriculaEstudante,
        String codigoDisciplina,
        String nomeDisciplina,
        String horario) {
}

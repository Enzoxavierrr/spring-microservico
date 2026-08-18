package br.pucrs.microdemo.microservico1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para matricular um estudante em uma disciplina e horario.")
public record MatriculaRequest(
        @Schema(description = "Numero de matricula do estudante.", example = "2024004")
        String matriculaEstudante,

        @Schema(description = "Codigo da disciplina escolhida.", example = "INF101")
        String codigoDisciplina,

        @Schema(description = "Horario escolhido para a disciplina.", example = "A")
        String horario) {
}

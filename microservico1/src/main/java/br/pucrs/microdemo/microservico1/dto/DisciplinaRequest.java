package br.pucrs.microdemo.microservico1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastrar uma disciplina em um horario.")
public record DisciplinaRequest(
        @Schema(description = "Codigo da disciplina.", example = "INF101")
        String codigo,

        @Schema(description = "Nome da disciplina.", example = "Programacao I")
        String nome,

        @Schema(description = "Codigo do horario da turma. Valores aceitos: A, B, C, D, E, F ou G.", example = "A")
        String horario) {
}

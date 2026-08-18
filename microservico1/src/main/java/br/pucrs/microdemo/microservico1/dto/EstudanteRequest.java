package br.pucrs.microdemo.microservico1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastrar um estudante.")
public record EstudanteRequest(
        @Schema(description = "Nome completo do estudante.", example = "Maria Silva")
        String nome,

        @Schema(description = "Numero de matricula do estudante.", example = "2024001")
        String matricula) {
}

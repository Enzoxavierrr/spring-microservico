package br.pucrs.microdemo.microservico1.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Comprovante gerado pelo microservico2.")
public record ComprovanteMatriculaResponse(
        @Schema(description = "Protocolo do comprovante.", example = "MAT-2024004-INF101-A")
        String protocolo,

        @Schema(description = "Mensagem de confirmacao da matricula.")
        String mensagem) {
}

package br.pucrs.microdemo.microservico1.dto;

import br.pucrs.microdemo.microservico1.domain.Estudante;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estudante cadastrado.")
public record EstudanteResponse(
        @Schema(description = "Identificador interno.", example = "1")
        Long id,

        @Schema(description = "Nome completo.", example = "Maria Silva")
        String nome,

        @Schema(description = "Numero de matricula.", example = "2024001")
        String matricula) {
    public static EstudanteResponse from(Estudante estudante) {
        return new EstudanteResponse(estudante.getId(), estudante.getNome(), estudante.getMatricula());
    }
}

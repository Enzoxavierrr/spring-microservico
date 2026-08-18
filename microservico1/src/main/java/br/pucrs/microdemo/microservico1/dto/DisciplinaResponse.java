package br.pucrs.microdemo.microservico1.dto;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Disciplina ofertada em um horario.")
public record DisciplinaResponse(
        @Schema(description = "Identificador interno.", example = "1")
        Long id,

        @Schema(description = "Codigo da disciplina.", example = "INF101")
        String codigo,

        @Schema(description = "Nome da disciplina.", example = "Programacao I")
        String nome,

        @Schema(description = "Horario da turma.", example = "A")
        String horario) {
    public static DisciplinaResponse from(Disciplina disciplina) {
        return new DisciplinaResponse(
                disciplina.getId(),
                disciplina.getCodigo(),
                disciplina.getNome(),
                disciplina.getHorario());
    }
}

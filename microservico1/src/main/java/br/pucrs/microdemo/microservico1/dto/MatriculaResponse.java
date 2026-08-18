package br.pucrs.microdemo.microservico1.dto;

import br.pucrs.microdemo.microservico1.domain.Matricula;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Matricula efetuada.")
public record MatriculaResponse(
        @Schema(description = "Identificador interno da matricula.", example = "1")
        Long id,

        @Schema(description = "Estudante matriculado.")
        EstudanteResponse estudante,

        @Schema(description = "Disciplina e horario escolhidos.")
        DisciplinaResponse disciplina) {
    public static MatriculaResponse from(Matricula matricula) {
        return new MatriculaResponse(
                matricula.getId(),
                EstudanteResponse.from(matricula.getEstudante()),
                DisciplinaResponse.from(matricula.getDisciplina()));
    }
}

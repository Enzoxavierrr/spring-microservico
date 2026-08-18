package br.pucrs.microdemo.microservico1.dto;

import br.pucrs.microdemo.microservico1.domain.Matricula;

public record ComprovanteMatriculaRequest(
        String nomeEstudante,
        String matriculaEstudante,
        String codigoDisciplina,
        String nomeDisciplina,
        String horario) {
    public static ComprovanteMatriculaRequest from(Matricula matricula) {
        return new ComprovanteMatriculaRequest(
                matricula.getEstudante().getNome(),
                matricula.getEstudante().getMatricula(),
                matricula.getDisciplina().getCodigo(),
                matricula.getDisciplina().getNome(),
                matricula.getDisciplina().getHorario());
    }
}

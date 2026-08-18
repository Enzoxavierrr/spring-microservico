package br.pucrs.microdemo.microservico1.service;

import br.pucrs.microdemo.microservico1.domain.Matricula;
import br.pucrs.microdemo.microservico1.dto.ComprovanteMatriculaResponse;

public record MatriculaEfetuada(
        Matricula matricula,
        ComprovanteMatriculaResponse comprovante) {
}

package br.pucrs.microdemo.microservico1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.microservico1.domain.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByEstudanteMatriculaAndDisciplinaId(String matriculaEstudante, Long disciplinaId);

    List<Matricula> findByEstudanteMatricula(String matriculaEstudante);
}

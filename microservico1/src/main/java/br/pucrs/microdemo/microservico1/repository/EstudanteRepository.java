package br.pucrs.microdemo.microservico1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.microservico1.domain.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    Optional<Estudante> findByMatricula(String matricula);

    List<Estudante> findByNomeContainingIgnoreCase(String nome);

    boolean existsByMatricula(String matricula);
}

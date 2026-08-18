package br.pucrs.microdemo.microservico1.repository;

import java.util.List;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByCodigoIgnoreCaseAndNomeIgnoreCaseAndHorarioIgnoreCase(String codigo, String nome, String horario);

    List<Disciplina> findByCodigoIgnoreCase(String codigo);

    List<Disciplina> findByCodigoIgnoreCaseAndHorarioIgnoreCase(String codigo, String horario);
}

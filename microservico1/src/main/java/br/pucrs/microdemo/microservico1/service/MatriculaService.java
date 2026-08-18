package br.pucrs.microdemo.microservico1.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import br.pucrs.microdemo.microservico1.domain.Estudante;
import br.pucrs.microdemo.microservico1.domain.Matricula;
import br.pucrs.microdemo.microservico1.repository.DisciplinaRepository;
import br.pucrs.microdemo.microservico1.repository.EstudanteRepository;
import br.pucrs.microdemo.microservico1.repository.MatriculaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final EstudanteRepository estudanteRepository;
    private final DisciplinaRepository disciplinaRepository;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            EstudanteRepository estudanteRepository,
            DisciplinaRepository disciplinaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.estudanteRepository = estudanteRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public Matricula matricular(String matriculaEstudante, String codigoDisciplina, String horario) {
        validarCamposObrigatorios(matriculaEstudante, codigoDisciplina, horario);

        Estudante estudante = estudanteRepository.findByMatricula(matriculaEstudante.trim())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Estudante nao encontrado."));

        Disciplina disciplina = localizarDisciplina(codigoDisciplina.trim(), horario.trim().toUpperCase());

        if (matriculaRepository.existsByEstudanteMatriculaAndDisciplinaId(estudante.getMatricula(), disciplina.getId())) {
            throw new ResponseStatusException(CONFLICT, "Estudante ja matriculado nesta disciplina e horario.");
        }

        return matriculaRepository.save(new Matricula(estudante, disciplina));
    }

    public List<Matricula> listarPorEstudante(String matriculaEstudante) {
        if (vazio(matriculaEstudante)) {
            throw new ResponseStatusException(BAD_REQUEST, "Matricula do estudante obrigatoria.");
        }

        return matriculaRepository.findByEstudanteMatricula(matriculaEstudante.trim());
    }

    private Disciplina localizarDisciplina(String codigoDisciplina, String horario) {
        List<Disciplina> disciplinas = disciplinaRepository.findByCodigoIgnoreCaseAndHorarioIgnoreCase(
                codigoDisciplina,
                horario);

        if (disciplinas.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Disciplina ou horario nao encontrado.");
        }

        if (disciplinas.size() > 1) {
            throw new ResponseStatusException(CONFLICT, "Mais de uma disciplina encontrada para este codigo e horario.");
        }

        return disciplinas.get(0);
    }

    private void validarCamposObrigatorios(String matriculaEstudante, String codigoDisciplina, String horario) {
        if (vazio(matriculaEstudante) || vazio(codigoDisciplina) || vazio(horario)) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "Campos obrigatorios: matriculaEstudante, codigoDisciplina e horario.");
        }
    }

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}

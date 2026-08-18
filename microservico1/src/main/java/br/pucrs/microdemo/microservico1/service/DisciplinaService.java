package br.pucrs.microdemo.microservico1.service;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import br.pucrs.microdemo.microservico1.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class DisciplinaService {

    private static final Set<String> HORARIOS_VALIDOS = Set.of("A", "B", "C", "D", "E", "F", "G");

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina cadastrar(Disciplina disciplina) {
        validarCamposObrigatorios(disciplina);

        String codigo = disciplina.getCodigo().trim();
        String nome = disciplina.getNome().trim();
        String horario = disciplina.getHorario().trim().toUpperCase();

        if (!HORARIOS_VALIDOS.contains(horario)) {
            throw new ResponseStatusException(BAD_REQUEST, "Horario invalido. Use um codigo entre A e G.");
        }

        boolean existeMesmoCodigoNomeHorario = disciplinaRepository
                .existsByCodigoIgnoreCaseAndNomeIgnoreCaseAndHorarioIgnoreCase(codigo, nome, horario);

        if (existeMesmoCodigoNomeHorario) {
            throw new ResponseStatusException(CONFLICT,
                    "Disciplina ja cadastrada para este mesmo horario.");
        }

        disciplina.setCodigo(codigo);
        disciplina.setNome(nome);
        disciplina.setHorario(horario);

        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    private void validarCamposObrigatorios(Disciplina disciplina) {
        if (disciplina == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Body da requisicao obrigatorio.");
        }

        if (vazio(disciplina.getCodigo()) || vazio(disciplina.getNome()) || vazio(disciplina.getHorario())) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "Campos obrigatorios: codigo, nome e horario.");
        }
    }

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}

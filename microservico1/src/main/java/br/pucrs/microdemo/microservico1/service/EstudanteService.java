package br.pucrs.microdemo.microservico1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.pucrs.microdemo.microservico1.domain.Estudante;
import br.pucrs.microdemo.microservico1.repository.EstudanteRepository;

@Service
public class EstudanteService {
    private final EstudanteRepository estudanteRepository;

    public EstudanteService(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    public Optional<Estudante> cadastrar(Estudante estudante) {
        validarEstudante(estudante);

        String matricula = estudante.getMatricula().trim();
        if (estudanteRepository.existsByMatricula(matricula)) {
            return Optional.empty();
        }

        Estudante estudanteParaSalvar = new Estudante(estudante.getNome().trim(), matricula);
        return Optional.of(estudanteRepository.save(estudanteParaSalvar));
    }

    public Optional<Estudante> consultarPorMatricula(String matricula) {
        return estudanteRepository.findByMatricula(matricula);
    }

    public List<Estudante> consultarPorNome(String nome) {
        return estudanteRepository.findByNomeContainingIgnoreCase(nome);
    }

    private void validarEstudante(Estudante estudante) {
        if (estudante == null || isBlank(estudante.getNome()) || isBlank(estudante.getMatricula())) {
            throw new IllegalArgumentException("Nome e matricula sao obrigatorios.");
        }
    }

    private boolean isBlank(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}

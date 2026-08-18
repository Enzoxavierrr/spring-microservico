package br.pucrs.microdemo.microservico2.business;

import org.springframework.stereotype.Service;

import br.pucrs.microdemo.microservico2.dto.NotificacaoMatriculaRequest;
import br.pucrs.microdemo.microservico2.dto.NotificacaoMatriculaResponse;

@Service
public class NotificacaoMatriculaService {
    public NotificacaoMatriculaResponse gerarComprovante(NotificacaoMatriculaRequest request) {
        validar(request);

        String matricula = request.matriculaEstudante().trim();
        String codigo = request.codigoDisciplina().trim().toUpperCase();
        String horario = request.horario().trim().toUpperCase();
        String protocolo = "MAT-" + matricula + "-" + codigo + "-" + horario;
        String mensagem = "Matricula efetuada com sucesso para "
                + request.nomeEstudante().trim()
                + " na disciplina "
                + request.nomeDisciplina().trim()
                + ", horario "
                + horario
                + ".";

        return new NotificacaoMatriculaResponse(protocolo, mensagem);
    }

    private void validar(NotificacaoMatriculaRequest request) {
        if (request == null
                || vazio(request.nomeEstudante())
                || vazio(request.matriculaEstudante())
                || vazio(request.codigoDisciplina())
                || vazio(request.nomeDisciplina())
                || vazio(request.horario())) {
            throw new IllegalArgumentException("Dados da matricula sao obrigatorios.");
        }
    }

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}

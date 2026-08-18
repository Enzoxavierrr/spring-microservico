package br.pucrs.microdemo.microservico1.client;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.microservico1.dto.ComprovanteMatriculaRequest;
import br.pucrs.microdemo.microservico1.dto.ComprovanteMatriculaResponse;

@Component
public class Microservico2Client {
    private final RestTemplate restTemplate;
    private final String microservico2Url;
    private final boolean comprovanteHabilitado;

    public Microservico2Client(
            RestTemplate restTemplate,
            @Value("${microservico2.url:https://doiscontainers-2}") String microservico2Url,
            @Value("${microservico2.comprovante.enabled:true}") boolean comprovanteHabilitado) {
        this.restTemplate = restTemplate;
        this.microservico2Url = microservico2Url;
        this.comprovanteHabilitado = comprovanteHabilitado;
    }

    public ComprovanteMatriculaResponse gerarComprovante(ComprovanteMatriculaRequest request) {
        if (!comprovanteHabilitado) {
            return gerarComprovanteLocal(request);
        }

        try {
            ComprovanteMatriculaResponse response = restTemplate.postForObject(
                    microservico2Url + "/notificacoes/matricula",
                    request,
                    ComprovanteMatriculaResponse.class);

            if (response == null) {
                throw new ResponseStatusException(BAD_GATEWAY, "Microservico2 nao retornou comprovante.");
            }

            return response;
        } catch (RestClientException exception) {
            throw new ResponseStatusException(BAD_GATEWAY, "Microservico2 indisponivel para gerar comprovante.");
        }
    }

    private ComprovanteMatriculaResponse gerarComprovanteLocal(ComprovanteMatriculaRequest request) {
        String codigo = request.codigoDisciplina().trim().toUpperCase();
        String horario = request.horario().trim().toUpperCase();
        String protocolo = "MAT-" + request.matriculaEstudante().trim() + "-" + codigo + "-" + horario;
        String mensagem = "Matricula efetuada com sucesso para "
                + request.nomeEstudante().trim()
                + " na disciplina "
                + request.nomeDisciplina().trim()
                + ", horario "
                + horario
                + ".";

        return new ComprovanteMatriculaResponse(protocolo, mensagem);
    }
}

package br.pucrs.microdemo.microservico2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.pucrs.microdemo.microservico2.business.CitationCollection;
import br.pucrs.microdemo.microservico2.business.NotificacaoMatriculaService;
import br.pucrs.microdemo.microservico2.dto.NotificacaoMatriculaRequest;

// @SpringBootTest

class Microservico2ApplicationTests {

	@Test
	void citationCollectionTest() {
		CitationCollection cc = new CitationCollection();
		
		assertNotNull(cc.getCitation());
	}

	@Test
	void geraComprovanteDeMatricula() {
		NotificacaoMatriculaService service = new NotificacaoMatriculaService();

		var comprovante = service.gerarComprovante(new NotificacaoMatriculaRequest(
				"Carlos Pereira",
				"2024004",
				"INF101",
				"Programacao I",
				"A"));

		assertNotNull(comprovante);
		assertNotNull(comprovante.protocolo());
	}

}

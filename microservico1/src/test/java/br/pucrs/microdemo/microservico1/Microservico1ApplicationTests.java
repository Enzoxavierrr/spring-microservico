package br.pucrs.microdemo.microservico1;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class Microservico1ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void cadastraEConsultaEstudantePorMatricula() throws Exception {
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Maria Silva\",\"matricula\":\"2024001\"}"))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "/estudantes/2024001"))
				.andExpect(jsonPath("$.nome").value("Maria Silva"))
				.andExpect(jsonPath("$.matricula").value("2024001"));

		mockMvc.perform(get("/estudantes/2024001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Maria Silva"))
				.andExpect(jsonPath("$.matricula").value("2024001"));
	}

	@Test
	void consultaEstudantesPorParteDoNome() throws Exception {
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Ana Costa\",\"matricula\":\"2024002\"}"))
				.andExpect(status().isCreated());
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Anabela Santos\",\"matricula\":\"2024003\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/estudantes").param("nome", "ana"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

}

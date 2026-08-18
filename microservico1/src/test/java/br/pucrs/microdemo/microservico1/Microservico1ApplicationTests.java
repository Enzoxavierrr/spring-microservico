package br.pucrs.microdemo.microservico1;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
	void documentacaoOpenApiEScalarDisponiveis() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.title").value("Microservico Academico"));

		mockMvc.perform(get("/scalar"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Scalar")));
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
	void listaTodosEstudantes() throws Exception {
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Ana Costa\",\"matricula\":\"2024002\"}"))
				.andExpect(status().isCreated());
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Anabela Santos\",\"matricula\":\"2024003\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/estudantes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.matricula == '2024002')]", hasSize(1)))
				.andExpect(jsonPath("$[?(@.matricula == '2024003')]", hasSize(1)));
	}

	@Test
	void matriculaEstudanteNaDisciplinaComHorario() throws Exception {
		mockMvc.perform(post("/estudantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Carlos Pereira\",\"matricula\":\"2024004\"}"))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/disciplinas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"codigo\":\"INF101\",\"nome\":\"Programacao I\",\"horario\":\"A\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.codigo").value("INF101"))
				.andExpect(jsonPath("$.horario").value("A"));

		mockMvc.perform(get("/disciplinas/INF101/horarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].horario").value("A"));

		mockMvc.perform(post("/matriculas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"matriculaEstudante\":\"2024004\",\"codigoDisciplina\":\"INF101\",\"horario\":\"A\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.estudante.matricula").value("2024004"))
				.andExpect(jsonPath("$.disciplina.codigo").value("INF101"))
				.andExpect(jsonPath("$.disciplina.horario").value("A"));
	}

}

package br.pucrs.microdemo.microservico1.controller;

import java.util.List;

import br.pucrs.microdemo.microservico1.dto.MatriculaRequest;
import br.pucrs.microdemo.microservico1.dto.MatriculaResponse;
import br.pucrs.microdemo.microservico1.service.MatriculaEfetuada;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.microservico1.service.MatriculaService;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matriculas", description = "Efetivacao e consulta de matriculas em disciplinas.")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Efetuar matricula",
            description = "Matricula um estudante em uma disciplina e horario escolhidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Matricula efetuada",
                    content = @Content(schema = @Schema(implementation = MatriculaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Campos obrigatorios ausentes", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudante, disciplina ou horario nao encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Matricula duplicada ou disciplina ambigua", content = @Content),
            @ApiResponse(responseCode = "502", description = "Microservico2 indisponivel para gerar comprovante", content = @Content)
    })
    public MatriculaResponse matricular(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para efetuar a matricula.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MatriculaRequest.class)))
            @RequestBody MatriculaRequest request) {
        MatriculaEfetuada matriculaEfetuada = matriculaService.matricular(
                request.matriculaEstudante(),
                request.codigoDisciplina(),
                request.horario());

        return MatriculaResponse.from(matriculaEfetuada.matricula(), matriculaEfetuada.comprovante());
    }

    @GetMapping("/estudantes/{matriculaEstudante}")
    @Operation(
            summary = "Listar matriculas do estudante",
            description = "Lista todas as disciplinas e horarios em que um estudante esta matriculado.")
    @ApiResponse(responseCode = "200", description = "Matriculas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MatriculaResponse.class))))
    public List<MatriculaResponse> listarPorEstudante(
            @Parameter(description = "Numero de matricula do estudante.", example = "2024004")
            @PathVariable String matriculaEstudante) {
        return matriculaService.listarPorEstudante(matriculaEstudante).stream()
                .map(MatriculaResponse::from)
                .toList();
    }
}

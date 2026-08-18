package br.pucrs.microdemo.microservico1.controller;

import br.pucrs.microdemo.microservico1.domain.Disciplina;
import br.pucrs.microdemo.microservico1.dto.DisciplinaRequest;
import br.pucrs.microdemo.microservico1.dto.DisciplinaResponse;
import br.pucrs.microdemo.microservico1.service.DisciplinaService;
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

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplinas", description = "Cadastro, consulta e escolha de horarios de disciplinas.")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar disciplina",
            description = "Cadastra uma oferta de disciplina em um horario especifico.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Disciplina cadastrada",
                    content = @Content(schema = @Schema(implementation = DisciplinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Campos obrigatorios ausentes ou horario invalido", content = @Content),
            @ApiResponse(responseCode = "409", description = "Disciplina ja cadastrada no mesmo horario", content = @Content)
    })
    public DisciplinaResponse cadastrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da disciplina.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DisciplinaRequest.class)))
            @RequestBody DisciplinaRequest request) {
        Disciplina disciplina = new Disciplina(null, request.codigo(), request.nome(), request.horario());
        return DisciplinaResponse.from(disciplinaService.cadastrar(disciplina));
    }

    @GetMapping
    @Operation(
            summary = "Listar disciplinas",
            description = "Lista todas as disciplinas ofertadas, incluindo seus horarios.")
    @ApiResponse(responseCode = "200", description = "Disciplinas listadas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisciplinaResponse.class))))
    public List<DisciplinaResponse> listar() {
        return disciplinaService.listar().stream()
                .map(DisciplinaResponse::from)
                .toList();
    }

    @GetMapping("/{codigo}/horarios")
    @Operation(
            summary = "Escolher horario da disciplina",
            description = "Lista os horarios disponiveis para uma disciplina pelo codigo.")
    @ApiResponse(responseCode = "200", description = "Horarios encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisciplinaResponse.class))))
    public List<DisciplinaResponse> listarHorarios(
            @Parameter(description = "Codigo da disciplina.", example = "INF101")
            @PathVariable String codigo) {
        return disciplinaService.listarHorariosPorCodigo(codigo).stream()
                .map(DisciplinaResponse::from)
                .toList();
    }
}

package br.pucrs.microdemo.microservico1.controller;

import java.net.URI;
import java.util.List;

import br.pucrs.microdemo.microservico1.domain.Estudante;
import br.pucrs.microdemo.microservico1.dto.EstudanteRequest;
import br.pucrs.microdemo.microservico1.dto.EstudanteResponse;
import br.pucrs.microdemo.microservico1.service.EstudanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Estudantes", description = "Cadastro e consulta de estudantes.")
public class EstudanteController {
    private final EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @PostMapping("/estudantes")
    @Operation(
            summary = "Cadastrar estudante",
            description = "Cria um estudante usando nome e numero de matricula.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudante cadastrado",
                    content = @Content(schema = @Schema(implementation = EstudanteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Nome ou matricula ausente", content = @Content),
            @ApiResponse(responseCode = "409", description = "Matricula ja cadastrada", content = @Content)
    })
    public ResponseEntity<EstudanteResponse> cadastrarEstudante(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do estudante.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EstudanteRequest.class)))
            @RequestBody EstudanteRequest request) {
        Estudante estudante = new Estudante(request.nome(), request.matricula());
        return estudanteService.cadastrar(estudante)
                .map(salvo -> ResponseEntity
                        .created(URI.create("/estudantes/" + salvo.getMatricula()))
                        .body(EstudanteResponse.from(salvo)))
                .orElse(ResponseEntity.status(409).build());
    }

    @GetMapping("/estudantes/{matricula}")
    @Operation(
            summary = "Consultar estudante por matricula",
            description = "Retorna um estudante pelo numero exato da matricula.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudante encontrado",
                    content = @Content(schema = @Schema(implementation = EstudanteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estudante nao encontrado", content = @Content)
    })
    public ResponseEntity<EstudanteResponse> consultarPorMatricula(
            @Parameter(description = "Numero de matricula do estudante.", example = "2024001")
            @PathVariable String matricula) {
        return estudanteService.consultarPorMatricula(matricula)
                .map(EstudanteResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estudantes")
    @Operation(
            summary = "Consultar estudantes por nome",
            description = "Busca estudantes por um pedaco do nome. Quando houver mais de um match, retorna uma lista.")
    @ApiResponse(responseCode = "200", description = "Lista de estudantes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EstudanteResponse.class))))
    public List<EstudanteResponse> consultarPorNome(
            @Parameter(description = "Parte do nome do estudante.", example = "ana")
            @RequestParam("nome") String nome) {
        return estudanteService.consultarPorNome(nome).stream()
                .map(EstudanteResponse::from)
                .toList();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarRequisicaoInvalida(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

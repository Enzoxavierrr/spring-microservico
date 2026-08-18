# Servicos e contratos da API

Documentacao interativa:

- Scalar: `https://localhost/scalar`
- OpenAPI JSON: `https://localhost/v3/api-docs`

## Servico de estudantes

Responsabilidade: cadastrar e consultar estudantes.

### `POST /estudantes`

Request:

```json
{
  "nome": "Maria Silva",
  "matricula": "2024001"
}
```

Responses:

- `201 Created`: estudante cadastrado.
- `400 Bad Request`: nome ou matricula ausente.
- `409 Conflict`: matricula ja cadastrada.

### `GET /estudantes/{matricula}`

Parametros:

- `matricula`: numero de matricula do estudante.

Responses:

- `200 OK`: estudante encontrado.
- `404 Not Found`: estudante nao encontrado.

### `GET /estudantes`

Response:

- `200 OK`: lista com todos os estudantes cadastrados.

## Servico de disciplinas

Responsabilidade: cadastrar disciplinas e listar horarios disponiveis.

### `POST /disciplinas`

Request:

```json
{
  "codigo": "INF101",
  "nome": "Programacao I",
  "horario": "A"
}
```

Responses:

- `201 Created`: disciplina cadastrada.
- `400 Bad Request`: campos ausentes ou horario invalido.
- `409 Conflict`: disciplina ja cadastrada para o mesmo horario.

### `GET /disciplinas`

Response:

- `200 OK`: lista de disciplinas ofertadas.

### `GET /disciplinas/{codigo}/horarios`

Parametros:

- `codigo`: codigo da disciplina.

Response:

- `200 OK`: lista de ofertas/horarios para a disciplina.

## Servico de matriculas

Responsabilidade: efetuar e consultar matriculas.

### `POST /matriculas`

Request:

```json
{
  "matriculaEstudante": "2024004",
  "codigoDisciplina": "INF101",
  "horario": "A"
}
```

Responses:

- `201 Created`: matricula efetuada.
- `400 Bad Request`: campos obrigatorios ausentes.
- `404 Not Found`: estudante, disciplina ou horario nao encontrado.
- `409 Conflict`: estudante ja matriculado ou disciplina ambigua.

### `GET /matriculas/estudantes/{matriculaEstudante}`

Parametros:

- `matriculaEstudante`: numero de matricula do estudante.

Response:

- `200 OK`: lista de matriculas do estudante.

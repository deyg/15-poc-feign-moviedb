# Movie DB Feign

> Antes de rodar esta PoC, crie uma conta gratuita no [The Movie Database](https://www.themoviedb.org), gere uma API Key v3 e um API Read Access Token v4 e configure as variaveis `TMDB_API_KEY` e `TMDB_API_TOKEN` conforme descrito na secao de configuracao (elas sao as unicas lidas pelo `application.yml`).

PoC em Spring Boot 3.3 + Java 21 que expoe uma API HTTP para consultar filmes populares e buscas do [The Movie Database (TMDb)](https://developer.themoviedb.org/). A integracao com o TMDb e feita via Spring Cloud OpenFeign e ja inclui interceptor para autenticacao por API key e/ou Bearer token.

## Arquitetura rapida
- `api`: controllers REST (`MovieController`) e tratador de excecoes (`RestExceptionHandler`) que padroniza erros de chamadas ao TMDb.
- `domain`: modelos (`Movie`, `MoviePage`) e `MovieService`, que aplica o fluxo de negocio antes de chamar o cliente externo.
- `infrastructure`: `MovieClient` (Feign) e `TmdbFeignConfig`, responsavel por anexar credenciais a cada requisicao.
- `application.yml`: define `tmdb.api.url`, `tmdb.api.key` e `tmdb.api.token`, lidos de variaveis de ambiente.

## Pre-requisitos
- JDK 21+
- Maven 3.9+ (nao ha wrapper no repositorio)
- Conta no TMDb com **API Key** (v3) e/ou **API Read Access Token** (v4)

## Configuracao das variaveis
`application.yml` agora requer apenas duas variaveis:
- `TMDB_API_KEY` — sua API Key v3.
- `TMDB_API_TOKEN` — seu API Read Access Token v4.

```powershell
# PowerShell (valido para a sessao atual)
$env:TMDB_API_KEY = '<sua-api-key>'
$env:TMDB_API_TOKEN = '<seu-token-bearer>'

# Persistir no Windows (abre um novo terminal depois)
setx TMDB_API_KEY "<sua-api-key>"
setx TMDB_API_TOKEN "<seu-token-bearer>"
```

```bash
# macOS / Linux
export TMDB_API_KEY="<sua-api-key>"
export TMDB_API_TOKEN="<seu-token-bearer>"
```

Se precisar apontar para outro ambiente (por exemplo, um mock), altere `tmdb.api.url` em `src/main/resources/application.yml`.

## Como executar
```bash
mvn spring-boot:run
# ou
mvn clean package
java -jar target/movie-db-feign-0.0.1-SNAPSHOT.jar
```
A aplicacao sobe em `http://localhost:8080`.

## Endpoints uteis
### GET `/movies/popular`
Parametros:
- `language` (opcional, default `pt-BR`)

Exemplo:
```bash
curl "http://localhost:8080/movies/popular?language=pt-BR"
```

### GET `/movies/search`
Parametros:
- `query` (obrigatorio)
- `language` (opcional, default `pt-BR`)

Exemplo:
```bash
curl "http://localhost:8080/movies/search?query=Noah&language=pt-BR"
```

### GET `/movies/{id}`
Parametros:
- `language` (opcional, default `pt-BR`)
- `id` (path) - identificador do TMDb recebido em outros endpoints

Exemplo:
```bash
curl "http://localhost:8080/movies/1156594?language=pt-BR"
```
Retorna um unico objeto `Movie`.

Os endpoints de listagem (`/popular` e `/search`) retornam o seguinte contrato:
```json
{
  "page": 1,
  "results": [
    {
      "id": 1156594,
      "title": "Nossa Culpa",
      "overview": "O casamento de Jenna e Lion marca o tao esperado reencontro...",
      "releaseDate": "2025-10-15",
      "posterPath": "/tPq8xqhsTYZzUGwMKuksa0eyeGZ.jpg",
      "voteAverage": 7.715
    }
  ]
}
```

## Tratamento de erros
Falhas vindas do TMDb (por exemplo, credenciais invalidas ou rate limit) sao capturadas por `RestExceptionHandler` e devolvidas como:
```json
{
  "error": "Falha ao consultar o TMDb",
  "status": 401,
  "message": "status 401 reading MovieClient#getPopularMovies(String,int)"
}
```

## Desenvolvimento
- `mvn test` executa a suite (atualmente sem testes, mas o comando garante que a pipeline continue consistente).
- Ajuste `MovieService` para novas regras e crie metodos equivalentes no `MovieClient` sempre que adicionar novos endpoints do TMDb.
- Para depurar chamadas HTTP, ative o log de Feign adicionando `logging.level.com.example.moviedb.infrastructure.client=DEBUG` no `application.yml`.

## Proximos passos sugeridos
1. Adicionar testes unitarios para `MovieService` usando mocks do Feign.
2. Criar endpoints adicionais (top rated, upcoming, detalhes do filme) reutilizando o client existente.
3. Proteger as credenciais com um cofre (Azure Key Vault, AWS Secrets Manager etc.) em ambientes produtivos.

## Sobre o Feign
O [OpenFeign](https://spring.io/projects/spring-cloud-openfeign) permite declarar clientes HTTP como interfaces Java, eliminando boilerplate de `RestTemplate` ou `WebClient`. No projeto, `MovieClient` define os endpoints do TMDb e o Spring gera dinamicamente a implementacao em tempo de execucao, aplicando interceptors (como `TmdbFeignConfig`) para autenticar cada chamada.

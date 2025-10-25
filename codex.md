📦 O formato ideal é um arquivo `.md` (Markdown) ou `.txt` — ambos funcionam bem como “script de prompts” que o Codex consegue interpretar em sequência.

---

## 💡 Estrutura sugerida do arquivo

**`codex-prompts-feign-moviedb.md`**

```markdown
# PoC Feign + MovieDB (Spring Boot 3.3 + Java 25)
Codex-Driven Process (CDP) — commits automáticos a001–a006.

---

## 🧠 a001 - Chassis Base
Crie o projeto base `movie-db-feign` em Java 25 + Spring Boot 3.3.  
Inclua as dependências:
- spring-boot-starter-web  
- spring-cloud-starter-openfeign  
- jackson-databind  

Estruture pacotes:
```

com.example.moviedb
├── api/
├── domain/
└── infrastructure/

````

Adicione `application.yml`:
```yaml
tmdb:
  api:
    url: https://api.themoviedb.org/3
    key: ${TMDB_API_KEY}
````

Gere `MovieDbApplication.java` com `@SpringBootApplication` e `@EnableFeignClients`.

💾 Commit: `a001 - setup: projeto base Spring Boot 3.3 com Feign e config TMDb`

---

## 🎥 a002 - Modelos de Domínio

Crie em `domain.model`:

```java
public record Movie(
    int id,
    String title,
    String overview,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("poster_path") String posterPath,
    @JsonProperty("vote_average") double voteAverage
) {}

public record MoviePage(
    int page,
    @JsonProperty("results") List<Movie> results
) {}
```

💾 Commit: `a002 - domain: records Movie e MoviePage criados`

---

## 📨 a003 - DTOs REST

Crie:

```java
// api/request/MovieRequest.java
public record MovieRequest(String language, String query) {}

// api/response/MovieResponse.java
public record MovieResponse(int page, List<Movie> results) {}
```

💾 Commit: `a003 - api: DTOs MovieRequest e MovieResponse adicionados`

---

## 🧭 a004 - Feign Client

```java
@FeignClient(name = "movieClient", url = "${tmdb.api.url}")
public interface MovieClient {

    @GetMapping("/movie/popular")
    MoviePage getPopularMovies(
        @RequestParam("api_key") String apiKey,
        @RequestParam("language") String language,
        @RequestParam("page") int page
    );

    @GetMapping("/search/movie")
    MoviePage searchMovies(
        @RequestParam("api_key") String apiKey,
        @RequestParam("language") String language,
        @RequestParam("query") String query
    );
}
```

💾 Commit: `a004 - infra: MovieClient Feign para TMDb criado`

---

## ⚙️ a005 - Service Layer

```java
@Service
public class MovieService {

    private final MovieClient movieClient;
    private final String apiKey;

    public MovieService(MovieClient movieClient, @Value("${tmdb.api.key}") String apiKey) {
        this.movieClient = movieClient;
        this.apiKey = apiKey;
    }

    public MoviePage getPopularMovies(String language) {
        return movieClient.getPopularMovies(apiKey, language, 1);
    }

    public MoviePage searchMovies(String language, String query) {
        return movieClient.searchMovies(apiKey, language, query);
    }
}
```

💾 Commit: `a005 - service: implementação do MovieService`

---

## 🌐 a006 - Controller REST

```java
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/popular")
    public MovieResponse getPopularMovies(@RequestParam(defaultValue = "pt-BR") String language) {
        var result = movieService.getPopularMovies(language);
        return new MovieResponse(result.page(), result.results());
    }

    @GetMapping("/search")
    public MovieResponse searchMovies(@RequestParam String query,
                                      @RequestParam(defaultValue = "pt-BR") String language) {
        var result = movieService.searchMovies(language, query);
        return new MovieResponse(result.page(), result.results());
    }
}
```

💾 Commit: `a006 - api: MovieController com endpoints populares e busca`

---

```

---

## ⚙️ Como o Codex deve agir

1. **Ler o arquivo sequencialmente** (`a001` → `a006`).  
2. **Executar cada bloco como prompt**.  
3. **Gerar o código correspondente.**  
4. **Commitar automaticamente** após cada etapa (`git add . && git commit -m "mensagem"`).

---

Posso gerar esse arquivo `.md` aqui no chat para você baixar (compactado como `.zip` ou direto como `.md`).  
👉 Deseja que eu gere o arquivo **`codex-prompts-feign-moviedb.md`** agora?  
Se sim, quer ele **em português** (como acima) ou **bilíngue (PT + EN)** para compatibilidade internacional do Codex?
```

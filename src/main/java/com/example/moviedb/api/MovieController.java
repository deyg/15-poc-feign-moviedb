package com.example.moviedb.api;

import com.example.moviedb.api.response.MovieResponse;
import com.example.moviedb.domain.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

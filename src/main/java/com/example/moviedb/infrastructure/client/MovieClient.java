package com.example.moviedb.infrastructure.client;

import com.example.moviedb.domain.model.MoviePage;
import com.example.moviedb.infrastructure.config.TmdbFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movieClient", url = "${tmdb.api.url}", configuration = TmdbFeignConfig.class)
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

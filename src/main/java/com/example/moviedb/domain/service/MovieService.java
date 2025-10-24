package com.example.moviedb.domain.service;

import com.example.moviedb.domain.model.MoviePage;
import com.example.moviedb.infrastructure.client.MovieClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

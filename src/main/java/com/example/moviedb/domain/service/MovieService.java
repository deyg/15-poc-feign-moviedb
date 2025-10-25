package com.example.moviedb.domain.service;

import com.example.moviedb.domain.model.MoviePage;
import com.example.moviedb.infrastructure.client.MovieClient;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieClient movieClient;

    public MovieService(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    public MoviePage getPopularMovies(String language) {
        return movieClient.getPopularMovies(language, 1);
    }

    public MoviePage searchMovies(String language, String query) {
        return movieClient.searchMovies(language, query);
    }
}

package com.example.moviedb.api.response;

import com.example.moviedb.domain.model.Movie;
import java.util.List;

public record MovieResponse(int page, List<Movie> results) {
}

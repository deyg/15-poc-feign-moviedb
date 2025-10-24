package com.example.moviedb.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record MoviePage(
        int page,
        @JsonProperty("results") List<Movie> results
) {
}

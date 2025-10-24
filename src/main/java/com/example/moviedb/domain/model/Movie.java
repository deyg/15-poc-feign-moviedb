package com.example.moviedb.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Movie(
        int id,
        String title,
        String overview,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("vote_average") double voteAverage
) {
}

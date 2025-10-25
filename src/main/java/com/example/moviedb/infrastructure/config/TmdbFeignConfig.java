package com.example.moviedb.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

@Configuration
public class TmdbFeignConfig {

    @Bean
    public RequestInterceptor tmdbAuthInterceptor(
            @Value("${tmdb.api.key:}") String apiKey,
            @Value("${tmdb.api.token:}") String apiToken) {
        return template -> {
            if (StringUtils.hasText(apiKey)) {
                template.query("api_key", apiKey);
            }
            if (StringUtils.hasText(apiToken)) {
                template.header(HttpHeaders.AUTHORIZATION, "Bearer " + apiToken);
            }
        };
    }
}

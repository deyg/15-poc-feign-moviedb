package com.example.moviedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieDbApplication.class, args);
    }
}

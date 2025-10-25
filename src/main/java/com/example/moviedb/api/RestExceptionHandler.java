package com.example.moviedb.api;

import feign.FeignException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException exception) {
        HttpStatus status = HttpStatus.resolve(exception.status());
        if (status == null) {
            status = HttpStatus.BAD_GATEWAY;
        }
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Falha ao consultar o TMDb");
        body.put("status", status.value());
        body.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }
}

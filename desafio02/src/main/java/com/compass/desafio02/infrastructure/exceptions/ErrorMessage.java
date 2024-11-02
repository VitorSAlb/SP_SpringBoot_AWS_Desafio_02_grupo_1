package com.compass.desafio02.infrastructure.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
    private int status;

    public ErrorMessage(HttpStatus status, String message, String path) {
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.message = message;
        this.path = request.getRequestURI();
        this.status = status.value();
    }
}

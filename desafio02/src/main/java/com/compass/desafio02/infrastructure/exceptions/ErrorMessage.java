package com.compass.desafio02.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private int statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ErrorMessage(HttpStatus status, String message, String path) {
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

package com.compass.desafio02.infrastructure.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

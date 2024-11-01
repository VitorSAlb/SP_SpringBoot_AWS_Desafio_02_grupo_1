package com.compass.desafio02.infrastructure.exceptions.user;

public class PasswordUpdateException extends RuntimeException {
    public PasswordUpdateException(String message) {
        super(message);
    }
}

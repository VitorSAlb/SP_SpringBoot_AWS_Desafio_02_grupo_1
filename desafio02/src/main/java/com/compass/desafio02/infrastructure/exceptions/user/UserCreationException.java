package com.compass.desafio02.infrastructure.exceptions.user;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}

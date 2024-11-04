package com.compass.desafio02.infrastructure.exceptions.user;

public class UserUpdateException extends RuntimeException {
    public UserUpdateException(String message) {
        super(message);
    }
}

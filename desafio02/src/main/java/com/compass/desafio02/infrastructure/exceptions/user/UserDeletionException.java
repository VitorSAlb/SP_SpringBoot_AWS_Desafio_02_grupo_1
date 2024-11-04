package com.compass.desafio02.infrastructure.exceptions.user;

public class UserDeletionException extends RuntimeException {
    public UserDeletionException(String message) {
        super(message);
    }
}

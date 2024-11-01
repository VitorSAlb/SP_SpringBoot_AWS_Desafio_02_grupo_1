package com.compass.desafio02.infrastructure.exceptions;

public class UserAlreadyAssignedException extends RuntimeException {
    public UserAlreadyAssignedException(String message) {
        super(message);
    }
}

package com.compass.desafio02.infrastructure.exceptions;

public class UserOrSubjectNotFoundException extends RuntimeException {
    public UserOrSubjectNotFoundException(String message) {
        super(message);
    }
}

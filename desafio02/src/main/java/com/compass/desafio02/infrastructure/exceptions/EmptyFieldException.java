package com.compass.desafio02.infrastructure.exceptions;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }
}

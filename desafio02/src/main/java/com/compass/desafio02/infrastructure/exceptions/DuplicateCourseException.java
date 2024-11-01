package com.compass.desafio02.infrastructure.exceptions;

public class DuplicateCourseException extends RuntimeException {
    public DuplicateCourseException(String message) {
        super(message);
    }
}

package com.compass.desafio02.infrastructure.exceptions;

public class CoordinatorOrCourseNotFoundException extends RuntimeException {
    public CoordinatorOrCourseNotFoundException(String message) {
        super(message);
    }
}

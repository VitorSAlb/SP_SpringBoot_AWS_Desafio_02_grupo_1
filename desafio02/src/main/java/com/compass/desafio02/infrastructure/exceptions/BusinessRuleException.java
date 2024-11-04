package com.compass.desafio02.infrastructure.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}

package com.compass.desafio02.infrastructure.exceptions;

public class CoordinatorAlreadyAssignedException extends RuntimeException {
  public CoordinatorAlreadyAssignedException(String message) {
    super(message);
  }
}

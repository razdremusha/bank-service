package org.example.exceptions;

public class AlreadyExist extends RuntimeException {

  public AlreadyExist(String message) {
    super(message);
  }
}

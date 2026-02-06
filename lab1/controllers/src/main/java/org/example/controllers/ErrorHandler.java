package org.example.controllers;

import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.AlreadyExistException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;
import org.example.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity handleAccountNotFoundException(AccountNotFoundException e) {
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AlreadyExistException.class)
  public ResponseEntity handleAlreadyExistException(AlreadyExistException e) {
    return new ResponseEntity(HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NegativeValueException.class)
  public ResponseEntity handleNegativeValueException(NegativeValueException e) {
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotEnoughMoneyException.class)
  public ResponseEntity handleNotEnoughMoney(NotEnoughMoneyException e) {
    return new ResponseEntity(HttpStatus.CONFLICT);
  }
}

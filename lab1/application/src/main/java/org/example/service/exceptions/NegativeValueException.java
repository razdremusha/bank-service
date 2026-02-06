package org.example.service.exceptions;

/**
 * Ошибка, указывающая на попытку взаимодействия с отрицательной суммой денег
 */
public class NegativeValueException extends Exception {

  public NegativeValueException() {
    super("Don't use negative value");
  }
}

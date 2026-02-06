package org.example.service.exceptions;

/**
 * Ошибка, указывающая на наличие такого юзера
 */
public class AlreadyExistException extends Exception {
    public AlreadyExistException(String message) {
        super(message + " already exist");
    }
}

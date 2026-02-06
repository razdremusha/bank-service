package org.example.service.exceptions;

/**
 * Ошибка, указывающая на то, что юзер не найден
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found in repository");
    }
}

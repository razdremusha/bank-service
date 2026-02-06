package org.example.service.exceptions;

/**
 * Ошибка, указывающая на отсутсвие аккаунта
 */
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("Account not found");
    }
    //Todo: переделать ошибки
}

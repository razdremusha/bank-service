package org.example.service.exceptions;

/**
 * Ошибка, указывающая на то, что на счете недосточно средств
 */
public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("Withdraw error, not enough money");
    }
}

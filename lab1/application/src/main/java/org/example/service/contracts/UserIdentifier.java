package org.example.service.contracts;

/**
 * Интерфейс для идентификации
 */
public interface UserIdentifier {

    /**
     * Функция для получения логина текущего юзера
     * @return Логин юзера
     */
    String getUserLogin();

    /**
     * Функция для перелогинивания
     * @param login Логин юзера
     */
    void setUserLogin(String login);

}

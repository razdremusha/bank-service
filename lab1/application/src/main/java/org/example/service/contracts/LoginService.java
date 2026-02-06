package org.example.service.contracts;

import org.example.service.exceptions.UserNotFoundException;

/**
 * Интерфейс для входа в аккаунт
 */
public interface LoginService {

  /**
   * Функция для входа в аккаунт
   *
   * @param login Логин аккаунта
   * @throws UserNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  void login(String login) throws UserNotFoundException;
}

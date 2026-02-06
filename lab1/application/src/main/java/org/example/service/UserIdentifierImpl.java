package org.example.service;

import org.example.service.contracts.UserIdentifier;
import org.springframework.stereotype.Service;

/**
 * Класс для идентификации
 */
@Service
public class UserIdentifierImpl implements UserIdentifier {

  /**
   * Логин юзера
   */
  public String userLogin;

  /**
   * Функция для получения логина текущего юзера
   *
   * @return Логин юзера
   */
  @Override
  public String getUserLogin() {
    return userLogin;
  }

  /**
   * Функция для перелогинивания
   *
   * @param login Логин юзера
   */
  @Override
  public void setUserLogin(String login) {
    userLogin = login;
  }
}

package org.example.service;

import java.util.Optional;
import org.example.UserRepository;
import org.example.entity.User;
import org.example.service.contracts.UserIdentifier;
import org.example.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Класс для входа в аккаунт
 */
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements org.example.service.contracts.LoginService {

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Репозиторий юзеров
   */
  private final UserRepository userRepository;

  /**
   * Конструктор логин сервиса
   *
   * @param userIdentifier Идентификатор юзера
   * @param userRepository Репозиторий юзеров
   */
  @Autowired
  public LoginServiceImpl(UserIdentifier userIdentifier, UserRepository userRepository) {
    this.userIdentifier = userIdentifier;
    this.userRepository = userRepository;
  }

  /**
   * Функция для входа в аккаунт
   *
   * @param login Логин аккаунта
   * @throws UserNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public void login(String login) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(login);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }
    userIdentifier.setUserLogin(login);
  }
}

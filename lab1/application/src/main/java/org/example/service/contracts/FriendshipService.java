package org.example.service.contracts;


import java.util.List;
import org.example.service.dto.UserDto;
import org.example.service.exceptions.UserNotFoundException;

/**
 * Интерфейс для взаиомодействия с друзьями
 */
public interface FriendshipService {

  /**
   * Функция для добавления друга
   *
   * @param login Логин аккаунта друга
   * @throws UserNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  void addFriend(String login) throws UserNotFoundException;

  /**
   * Функция удаления друга
   *
   * @param login Логин аккаунта друга
   */
  void deleteFriend(String login);

  List<UserDto> getFriendsByLogin(String login) throws UserNotFoundException;
}

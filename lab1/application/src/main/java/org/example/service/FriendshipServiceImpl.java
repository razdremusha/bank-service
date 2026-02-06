package org.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.FriendshipRepository;
import org.example.UserRepository;
import org.example.entity.FriendPair;
import org.example.entity.User;
import org.example.service.contracts.UserIdentifier;

import org.example.service.dto.UserDto;
import org.example.service.dto.UserMapping;
import org.example.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для взаиомодействия с друзьями
 */
@Service
public class FriendshipServiceImpl implements org.example.service.contracts.FriendshipService {

  /**
   * Сервис юзера
   */
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Репозиторий юзеров
   */
  private final UserRepository userRepository;

  private final FriendshipRepository friendshipRepository;

  /**
   * Конструктор сервиса дружбы
   *
   * @param userIdentifier Идентификатор юзера
   * @param userRepository Репозиторий юзеров
   */
  @Autowired
  public FriendshipServiceImpl(UserIdentifier userIdentifier,
      UserRepository userRepository, FriendshipRepository friendshipRepository) {
    this.userIdentifier = userIdentifier;
    this.userRepository = userRepository;
    this.friendshipRepository = friendshipRepository;
  }

  /**
   * Функция для добавления друга
   *
   * @param login Логин аккаунта друга
   * @throws UserNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public void addFriend(String login) throws UserNotFoundException {
    Optional<User> friend = userRepository.findById(login);
    if (friend.isEmpty()) {
      throw new UserNotFoundException();
    }
    FriendPair friendPair = new FriendPair();
    friendPair.setFriend(friend.get());
    friendPair.setUser(userRepository.findById(userIdentifier.getUserLogin()).get());
    friendshipRepository.save(friendPair);
  }

  /**
   * Функция удаления друга
   *
   * @param login Логин аккаунта друга
   */
  @Override
  public void deleteFriend(String login) {
    FriendPair friendPair = new FriendPair();
    friendPair.setFriend(userRepository.findById(login).get());
    friendPair.setUser(userRepository.findById(userIdentifier.getUserLogin()).get());
    friendshipRepository.delete(friendPair);
  }

  @Override
  public List<UserDto> getFriendsByLogin(String login) throws UserNotFoundException {
    Optional<User> user = userRepository.findById(login);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }
    return friendshipRepository.findFriendsByUser(user.get()).stream().map(UserMapping::toDto).collect(
        Collectors.toList());
  }
}

package org.example.scenario.addfriend;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.FriendshipService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий добавления друга
 */
public class ScenarioProviderAddFriend implements ScenarioProvider {

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;
  /**
   * Сервис для взаимодействия с друзьями
   */
  private final FriendshipService friendshipService;

  /**
   * Конструктор класса, выдающего сценарий добавления друга
   *
   * @param userIdentifier    Идентификатор юзера
   * @param friendshipService Сервис для взаимодействия с друзьями
   */
  public ScenarioProviderAddFriend(UserIdentifier userIdentifier,
      FriendshipService friendshipService) {
    this.userIdentifier = userIdentifier;
    this.friendshipService = friendshipService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий добавления друга
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioAddFriend(friendshipService);
  }
}

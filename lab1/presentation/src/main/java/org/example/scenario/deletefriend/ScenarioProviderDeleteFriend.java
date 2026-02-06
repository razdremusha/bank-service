package org.example.scenario.deletefriend;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.FriendshipService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий удаления друга
 */
public class ScenarioProviderDeleteFriend implements ScenarioProvider {

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;
  /**
   * Сервис для взаимодействия с друзьями
   */
  private final FriendshipService friendshipService;

  /**
   * Конструктор класса, выдающего сценарий удаления друга
   *
   * @param userIdentifier    Идентификатор юзера
   * @param friendshipService Сервис для взаимодействия с друзьями
   */
  public ScenarioProviderDeleteFriend(UserIdentifier userIdentifier,
      FriendshipService friendshipService) {
    this.userIdentifier = userIdentifier;
    this.friendshipService = friendshipService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий удаления друга
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioDeleteFriend(friendshipService);
  }
}

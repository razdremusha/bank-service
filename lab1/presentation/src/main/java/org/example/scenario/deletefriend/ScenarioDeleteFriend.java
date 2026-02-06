package org.example.scenario.deletefriend;

import org.example.Scenario;
import org.example.service.contracts.FriendshipService;

/**
 * Класс сценария удаления друга
 */
public class ScenarioDeleteFriend implements Scenario {

  /**
   * Название сценария
   */
  private final String name = "Delete Friend";
  /**
   * Сервис взаимодействия с друзьями
   */
  private final FriendshipService friendshipService;

  /**
   * Конструктор сценария удаления друга
   *
   * @param friendshipService Сервис взаимодействия с друзьями
   */
  public ScenarioDeleteFriend(FriendshipService friendshipService) {
    this.friendshipService = friendshipService;
  }

  /**
   * Функция получает имя сценария
   *
   * @return Имя
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Функция, приводящая сценарий в работу
   */
  @Override
  public void run() {
    System.out.println("Enter your new friend login");
    String login = System.console().readLine();
    friendshipService.deleteFriend(login);

  }
}

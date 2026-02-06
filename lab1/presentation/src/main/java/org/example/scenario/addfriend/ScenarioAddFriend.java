package org.example.scenario.addfriend;

import org.example.Scenario;
import org.example.service.contracts.FriendshipService;
import org.example.service.exceptions.UserNotFoundException;

/**
 * Класс сценария добавления друга
 */
public class ScenarioAddFriend implements Scenario {

  /**
   * Название сценария
   */
  private final String name = "Add Friend";
  /**
   * Сервис взаимодействия с друзьями
   */
  private final FriendshipService friendshipService;

  /**
   * Конструктор сценария добавления друга
   *
   * @param friendshipService Сервис взаимодействия с друзьями
   */
  public ScenarioAddFriend(FriendshipService friendshipService) {
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
    try {
      friendshipService.addFriend(login);
    } catch (UserNotFoundException e) {
      System.out.println("Whoops your friend doesn't exist");
    }
  }
}

package org.example.scenario.showinfo;

import org.example.Scenario;
import org.example.service.contracts.UserService;

/**
 * Класс сценария показания информации о юзере
 */
public class ScenarioShowInfo implements Scenario {

  /**
   * Сервис для взаимодействия с юзерами
   */
  private final UserService userService;

  /**
   * Название сценария
   */
  private final String name = "Show info about me";

  /**
   * Конструктор сценария показания информации о юзере
   *
   * @param userService Сервис для взаимодействия с юзерами
   */
  public ScenarioShowInfo(UserService userService) {
    this.userService = userService;
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
    System.out.println("===Info==");
    userService.showInfo();
  }
}

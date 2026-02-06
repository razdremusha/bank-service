package org.example.scenario.showinfo;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.UserIdentifier;
import org.example.service.contracts.UserService;

/**
 * Класс, выдающий сценарий показания информации о юзере
 */
public class ScenarioProviderShowInfo implements ScenarioProvider {

  /**
   * Сервис для работы с юзерами
   */
  private final UserService userService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Конструктор класса, выдающего сценарий показания информации о юзере
   *
   * @param userService    Сервис для работы с юзерами
   * @param userIdentifier Идентификатор юзера
   */
  public ScenarioProviderShowInfo(UserService userService, UserIdentifier userIdentifier) {
    this.userService = userService;
    this.userIdentifier = userIdentifier;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий показания информации о юзере
   */
  @Override
  public Scenario tryGetScenario() {
    Scenario result = null;
    if (userIdentifier.getUserLogin() != null) {
      result = new ScenarioShowInfo(userService);
    }
    return result;
  }
}

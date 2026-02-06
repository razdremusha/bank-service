package org.example.scenario.createuser;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.UserService;

/**
 * Класс, выдающий сценарий создания юзера
 */
public class ScenarioProviderCreationUser implements ScenarioProvider {

  /**
   * Сервис для работы с юзерами
   */
  private final UserService userService;

  /**
   * Конструктор класса, выдающего сценарий создания юзера
   *
   * @param userService Сервис для работы с юзерами
   */
  public ScenarioProviderCreationUser(UserService userService) {
    this.userService = userService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий создания юзера
   */
  @Override
  public Scenario tryGetScenario() {
    ScenarioCreationUser scenario = new ScenarioCreationUser(userService);
    return scenario;
  }
}

package org.example.scenario.login;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.LoginService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий входа в аккаунт
 */
public class ScenarioProviderLogin implements ScenarioProvider {

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;
  /**
   * Сервис для входа в аккаунт
   */
  private final LoginService loginService;

  /**
   * Конструктор класса, выдающего сценарий входа в аккаунт
   *
   * @param userIdentifier Идентификатор юзера
   * @param loginService   Сервис для входа в аккаунт
   */
  public ScenarioProviderLogin(UserIdentifier userIdentifier, LoginService loginService) {
    this.userIdentifier = userIdentifier;
    this.loginService = loginService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий входа в аккаунт
   */
  @Override
  public Scenario tryGetScenario() {
    return new ScenarioLogin(loginService, userIdentifier);
  }
}

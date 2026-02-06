package org.example.scenario.login;

import org.example.Scenario;
import org.example.service.contracts.LoginService;
import org.example.service.contracts.UserIdentifier;
import org.example.service.exceptions.UserNotFoundException;

/**
 * Класс сценария входа в аккаунт
 */
public class ScenarioLogin implements Scenario {

  /**
   * Сервис для входа в аккаунт
   */
  private final LoginService loginService;

  /**
   * Название сценария
   */
  private final String name = "Login user";

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Конструтор сценария входа в систему
   *
   * @param loginService   Сервис для входа в аккаунт
   * @param userIdentifier Идентификатор юзера
   */
  public ScenarioLogin(LoginService loginService, UserIdentifier userIdentifier) {
    this.loginService = loginService;
    this.userIdentifier = userIdentifier;
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
    System.out.println("Enter login: ");
    String login = System.console().readLine();
    try {
      loginService.login(login);
      System.out.println("Logged in as " + login);
    } catch (UserNotFoundException e) {
      System.out.println("Whoops user with such login not found");
    }
  }
}

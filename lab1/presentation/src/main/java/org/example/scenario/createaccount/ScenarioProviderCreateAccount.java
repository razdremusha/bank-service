package org.example.scenario.createaccount;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий создания аккаунта
 */
public class ScenarioProviderCreateAccount implements ScenarioProvider {

  /**
   * Сервис для взаимодейсвтия с аккаунтами
   */
  private final AccountService accountService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Конструктор класса, выдающего сценарий создания аккаунта
   *
   * @param accountService Сервис для взаимодейсвтия с аккаунтами
   * @param userIdentifier Идентификатор юзера
   */
  public ScenarioProviderCreateAccount(AccountService accountService,
      UserIdentifier userIdentifier) {
    this.accountService = accountService;
    this.userIdentifier = userIdentifier;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий создания аккаунта
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioCreateAccount(accountService);
  }

}

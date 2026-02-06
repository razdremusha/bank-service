package org.example.scenario.withdraw;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий снятия денег
 */
public class ScenarioProviderWithdraw implements ScenarioProvider {

  /**
   * Сервис для взаимодействия с операциями
   */
  private final OperationService operationService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Сервис для взаимодейсвтия с аккаунтами
   */
  private final AccountService accountService;

  /**
   * Конструктор класса, выдающего сценарий снятия денег
   *
   * @param operationService Сервис для взаимодействия с операциями
   * @param userIdentifier   Идентификатор юзера
   * @param accountService   Сервис для взаимодейсвтия с аккаунтами
   */
  public ScenarioProviderWithdraw(OperationService operationService, UserIdentifier userIdentifier,
      AccountService accountService) {
    this.operationService = operationService;
    this.userIdentifier = userIdentifier;
    this.accountService = accountService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий снятия денег
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioWithdraw(operationService, accountService, userIdentifier);
  }
}

package org.example.scenario.getbalance;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий получения баланса
 */
public class ScenarioProviderGetBalance implements ScenarioProvider {

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
   * Конструктор класса, выдающего сценарий получения баланса
   *
   * @param operationService Сервис для взаимодействия с операциями
   * @param userIdentifier   Идентификатор юзера
   * @param accountService   Сервис для взаимодейсвтия с аккаунтами
   */
  public ScenarioProviderGetBalance(OperationService operationService,
      UserIdentifier userIdentifier,
      AccountService accountService) {
    this.operationService = operationService;
    this.userIdentifier = userIdentifier;
    this.accountService = accountService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий получения баланса
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioGetBalance(operationService, accountService, userIdentifier);
  }
}

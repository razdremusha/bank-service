package org.example.scenario.transfer;

import org.example.Scenario;
import org.example.ScenarioProvider;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;

/**
 * Класс, выдающий сценарий перевода денег
 */
public class ScenarioProviderTransfer implements ScenarioProvider {

  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;
  /**
   * Сервис для взаимодейсвтия с аккаунтами
   */
  private final AccountService accountService;

  /**
   * Сервис для взаимодействия с операциями
   */
  private final OperationService operationService;

  /**
   * Конструктор класса, выдающего сценарий перевода денег
   *
   * @param userIdentifier   Идентификатор юзера
   * @param accountService   Сервис для взаимодейсвтия с аккаунтами
   * @param operationService Сервис для взаимодействия с операциями
   */
  public ScenarioProviderTransfer(UserIdentifier userIdentifier, AccountService accountService,
      OperationService operationService) {
    this.userIdentifier = userIdentifier;
    this.accountService = accountService;
    this.operationService = operationService;
  }

  /**
   * Функция, которая получает сценарий, если он есть
   *
   * @return Полученный сценарий перевода денег
   */
  @Override
  public Scenario tryGetScenario() {
    if (userIdentifier.getUserLogin() == null) {
      return null;
    }
    return new ScenarioTransfer(operationService, accountService, userIdentifier);
  }
}

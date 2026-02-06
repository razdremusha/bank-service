package org.example.scenario.getbalance;

import java.math.BigDecimal;
import org.example.Scenario;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;
import org.example.service.exceptions.AccountNotFoundException;

/**
 * Класс сценария получения баланса
 */
public class ScenarioGetBalance implements Scenario {

  /**
   * Сервис для взаимодействия с операциями
   */
  private final OperationService operationService;


  /**
   * Название сценария
   */
  private final String name = "Get Balance";
  /**
   * Сервис для взаимодействия с аккаунтами
   */
  private final AccountService accountService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Конструктор сценария получения баланса
   *
   * @param operationService Сервис для взаимодействия с операциями
   * @param accountService   Сервис для взаимодействия с аккаунтами
   * @param userIdentifier   Идентификатор юзера
   */
  public ScenarioGetBalance(OperationService operationService, AccountService accountService,
      UserIdentifier userIdentifier) {
    this.operationService = operationService;
    this.accountService = accountService;
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
    System.out.println("Enter accountId");
    long accountId = Long.parseLong(System.console().readLine());

    try {
      if (!accountService.isOwner(accountId, userIdentifier.getUserLogin())) {
        System.out.println("You are not owner of this account");
        return;
      }
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      BigDecimal balance = operationService.getBalance(accountId);
      System.out.println(balance);
    } catch (AccountNotFoundException e) {
      System.out.println("Account not found");
    }
  }
}

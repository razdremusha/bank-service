package org.example.scenario.deposit;

import java.math.BigDecimal;
import org.example.Scenario;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.NegativeValueException;

/**
 * Класс сценария пополнения денег
 */
public class ScenarioDeposit implements Scenario {

  /**
   * Сервис для взаимодействия с операциями
   */
  private final OperationService operationService;

  /**
   * Название сценария
   */
  private final String name = "Deposit";
  /**
   * Сервис для взаимодействия с аккаунтами
   */
  private final AccountService accountService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;

  /**
   * Конструктор сценария пополнения денег
   *
   * @param operationService Сервис для взаимодействия с операциями
   * @param accountService   Сервис для взаимодействия с аккаунтами
   * @param userIdentifier   Идентификатор юзера
   */
  public ScenarioDeposit(OperationService operationService, AccountService accountService,
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
    System.out.println("Enter account ID");
    long accountId = Long.parseLong(System.console().readLine());
    try {
      if (!accountService.isOwner(accountId, userIdentifier.getUserLogin())) {
        System.out.println("You are not owner of this account, please use transfer instead");
        return;
      }
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Enter amount of money: ");
    BigDecimal amount = new BigDecimal(System.console().readLine());
    try {
      operationService.deposit(accountId, amount);
    } catch (AccountNotFoundException e) {
      System.out.println("Whoops, account not found");
    } catch (NegativeValueException e) {
      System.out.println("Whoops, don't use negative money. They don't exist");
    }
  }
}

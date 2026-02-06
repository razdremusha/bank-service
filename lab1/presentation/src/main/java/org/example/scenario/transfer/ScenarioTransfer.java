package org.example.scenario.transfer;

import java.math.BigDecimal;
import org.example.Scenario;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserIdentifier;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;

/**
 * Класс сценария перевода денег
 */
public class ScenarioTransfer implements Scenario {

  /**
   * Сервис для взаимодействия с операциями
   */
  private final OperationService operationService;
  /**
   * Сервис для взаимодействия с аккаунтами
   */
  private final AccountService accountService;
  /**
   * Идентификатор юзера
   */
  private final UserIdentifier userIdentifier;
  /**
   * Название сценария
   */
  private final String name = "Transfer";

  /**
   * Конструктор сценария перевода денег
   *
   * @param operationService Сервис для взаимодействия с операциями
   * @param accountService   Сервис для взаимодействия с аккаунтами
   * @param userIdentifier   Идентификатор юзера
   */
  public ScenarioTransfer(OperationService operationService, AccountService accountService,
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
    System.out.println("Enter accountId: ");
    long fromId = Long.parseLong(System.console().readLine());
    try {
      if (!accountService.isOwner(fromId, userIdentifier.getUserLogin())) {
        System.out.println("You're not owner of this account");
        return;
      }
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Enter accountId to transfer: ");
    long toId = Long.parseLong(System.console().readLine());
    System.out.println("Enter amount of money: ");
    BigDecimal amount = BigDecimal.valueOf(Long.parseLong(System.console().readLine()));
    try {
      operationService.transfer(fromId, toId, amount);
    } catch (AccountNotFoundException e) {
      System.out.println("Account not found!");
    } catch (NegativeValueException e) {
      System.out.println("Negative amount entered!");
    } catch (NotEnoughMoneyException e) {
      System.out.println("Not enough money to transfer. Maybe you don't count the commission?");
    }
  }
}

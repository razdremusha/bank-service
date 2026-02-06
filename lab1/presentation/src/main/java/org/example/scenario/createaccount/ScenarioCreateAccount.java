package org.example.scenario.createaccount;

import java.math.BigDecimal;
import org.example.Scenario;
import org.example.service.contracts.AccountService;

/**
 * Класс сценария создания аккаунта
 */
public class ScenarioCreateAccount implements Scenario {

  /**
   * Названия сценария
   */
  private final String name = "Create Account";

  /**
   * Сервис для взаимодействия с аккаунтами
   */
  private final AccountService accountService;

  /**
   * Конструктор сценария создания аккаунта
   *
   * @param accountService Сервис для взаимодействия с аккаунтами
   */
  public ScenarioCreateAccount(AccountService accountService) {
    this.accountService = accountService;
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
    System.out.print("Enter balance: ");
    BigDecimal balance = new BigDecimal(System.console().readLine());
    var myAccount = accountService.createAccount(balance);
    System.out.print("Remember your ID! Id:" + myAccount.accountId());
  }
}

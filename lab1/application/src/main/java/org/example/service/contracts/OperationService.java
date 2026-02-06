package org.example.service.contracts;

import java.util.List;
import org.example.entity.Account;
import org.example.entity.OperationType;
import org.example.service.dto.AccountDto;
import org.example.service.dto.OperationDto;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;

/**
 * Интерфейс для взаимодействия с операциями
 */
public interface OperationService {

  /**
   * Функция для получения баланса
   *
   * @param accountId Id нужного аккаунта
   * @return Баланс аккаунта
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  BigDecimal getBalance(long accountId) throws AccountNotFoundException;

  /**
   * Функция для снятия денег с баланса
   *
   * @param accountId Id нужного аккаунта
   * @param amount    Снимаемая суммая
   * @throws NotEnoughMoneyException  Выдаем ошибку, если на счете недостаточно средств
   * @throws NegativeValueException   Выдаем ошибку, если пытаемся снять отрицательные деньги
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  void withdraw(long accountId, BigDecimal amount)
      throws NotEnoughMoneyException, NegativeValueException, AccountNotFoundException;

  /**
   * Функция для пополнения денег
   *
   * @param accountId Id нужного аккаунта
   * @param amount    Пополняемая сумма
   * @throws NegativeValueException   Выдаем ошибку, если пытаемся положить отрицательные деньги
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  void deposit(long accountId, BigDecimal amount)
      throws NegativeValueException, AccountNotFoundException;

  /**
   * Функция для перевода денег
   *
   * @param fromAccountId Id аккаунта, с которого переводим
   * @param toAccountId   Id аккаунта, на который кладем
   * @param amount        Сумма перевода
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   * @throws NotEnoughMoneyException  Выдаем ошибку, если на счете недостаточно средств
   * @throws NegativeValueException   Выдаем ошибку, если пытаемся перевести отрицательные деньги
   */
  void transfer(long fromAccountId, long toAccountId, BigDecimal amount)
      throws AccountNotFoundException, NotEnoughMoneyException, NegativeValueException;

  List<OperationDto> getOperations();

  List<OperationDto> getOperationsByAccountId(Long accountId) throws AccountNotFoundException;

  List<OperationDto> getOperationsByType(OperationType type);

  List<OperationDto> getOperationsByAccountAndType(Long accountId, OperationType type)
      throws AccountNotFoundException;

  List<OperationDto> getFilteredOperations(Long accountId, OperationType type)
      throws AccountNotFoundException;

  public AccountDto updateInfo(Account account, BigDecimal amount, OperationType operationType);
}

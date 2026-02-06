package org.example.service.contracts;

import java.math.BigDecimal;
import java.util.List;
import org.example.entity.Account;
import org.example.entity.OperationType;
import org.example.service.dto.AccountDto;
import org.example.service.exceptions.AccountNotFoundException;

/**
 * Интерфейс для взаимодействия с аккаунтами
 */
public interface AccountService {

  /**
   * Функция для создания аккаунта
   *
   * @param balance Изначальный баланс аккаунта
   * @return Возвращаем созданный аккаунт
   */
  AccountDto createAccount(BigDecimal balance);

  /**
   * Функция для получения своих аккаунтов
   *
   * @param owner Владелeц
   * @return Лист аккаунтов владельца
   */
  List<AccountDto> getAccountsByOwner(String owner);

  /**
   * Функция проверяет владелец ли
   *
   * @param Id         Id аккаунта
   * @param loginOwner Логин владельца
   * @return Факт владения аккаунтом
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  boolean isOwner(long Id, String loginOwner) throws AccountNotFoundException;


  List<AccountDto> getAllAccounts();

  AccountDto getAccountById(long id) throws AccountNotFoundException;

}

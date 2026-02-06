package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.example.AccountRepository;
import org.example.UserRepository;
import org.example.entity.Account;
import org.example.entity.User;
import org.example.service.contracts.UserIdentifier;
import org.example.service.dto.AccountDto;
import org.example.service.dto.AccountMapping;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.kafka.AccountMutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для взаимодействия с аккаунтами
 */
@Service
public class AccountServiceImpl implements org.example.service.contracts.AccountService {

  /**
   * Репозиторий с аккаунтами
   */

  private final AccountRepository accountRepository;

  private final UserRepository userRepository;

  /**
   * Идентификатор юзера
   */

  private final UserIdentifier userIdentifier;

  /**
   * Конструктор аккаунт сервиса
   *
   * @param accountRepository репозиторий аккаунтов
   * @param userIdentifier    идентификатор юзера
   */
  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository,
      UserIdentifier userIdentifier) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
    this.userIdentifier = userIdentifier;
  }

  /**
   * Функция для создания аккаунта
   *
   * @param balance Изначальный баланс аккаунта
   * @return Возвращаем созданный аккаунт
   */
  @Override
  @AccountMutable
  public AccountDto createAccount(BigDecimal balance) {
    Account account = new Account();
    account.setBalance(balance);
    Optional<User> user = userRepository.findById(userIdentifier.getUserLogin());

    account.setOwner(user.isPresent() ? user.get() : null);
    accountRepository.save(account);
    return AccountMapping.toDto(account);
  }

  /**
   * Функция для получения своих аккаунтов
   *
   * @param owner Владелец
   * @return Лист аккаунтов владельца
   */
  @Override
  public List<AccountDto> getAccountsByOwner(String owner) {
    var user = userRepository.findById(owner);
    if (user.isEmpty())
      return new ArrayList<>();
    return this.accountRepository.findAccountsByOwner(user.get())
        .stream()
        .map(AccountMapping::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Функция проверяет владелец ли
   *
   * @param Id         Id аккаунта
   * @param loginOwner Логин владельца
   * @return Факт владения аккаунтом
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public boolean isOwner(long Id, String loginOwner) throws AccountNotFoundException {
    var account = accountRepository.findById(Id);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get().getOwner().getLogin().equals(loginOwner);
  }

  @Override
  public List<AccountDto> getAllAccounts() {
    return accountRepository.findAll().stream().map(AccountMapping::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public AccountDto getAccountById(long id) throws AccountNotFoundException {
    Optional<Account> account = accountRepository.findById(id);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return AccountMapping.toDto(account.get());
  }

}

package org.example.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import org.example.AccountRepository;
import org.example.FriendshipRepository;
import org.example.OperationRepository;
import org.example.UserRepository;
import org.example.entity.Account;
import org.example.entity.Operation;
import org.example.entity.OperationType;
import org.example.entity.User;
import org.example.service.contracts.OperationService;
import org.example.service.dto.AccountDto;
import org.example.service.dto.AccountMapping;
import org.example.service.dto.OperationDto;
import org.example.service.dto.OperationMapping;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;
import org.example.service.kafka.AccountMutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для взаимодействия с операциями
 */
@Service
public class OperationServiceImpl implements OperationService {

  /**
   * Репозиторий аккаунтов
   */
  private final AccountRepository accountRepository;
  /**
   * Репозиторий юзеров
   */

  private final UserRepository userRepository;


  private final OperationRepository operationRepository;


  private final FriendshipRepository friendshipRepository;

  /**
   * Конструктор сервиса операций
   *
   * @param accountRepository Репозиторий аккаунтов
   * @param userRepository    Репозиторий юзеров
   */
  @Autowired
  public OperationServiceImpl(AccountRepository accountRepository, UserRepository userRepository,
      OperationRepository operationRepository, FriendshipRepository friendshipRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
    this.operationRepository = operationRepository;
    this.friendshipRepository = friendshipRepository;
  }

  @Override
  @AccountMutable
  public AccountDto updateInfo(Account account, BigDecimal amount, OperationType operationType) {
    BigDecimal difference = amount.subtract(account.getBalance());
    Account newAccount = new Account();
    newAccount.setBalance(amount);
    newAccount.setId(account.getId());
    newAccount.setOwner(account.getOwner());

    var operation = new Operation();
    operation.setDifference(difference);
    operation.setOperationType(operationType);
    operation.setAccount(newAccount);

    accountRepository.save(newAccount);
    operationRepository.save(operation);

    return AccountMapping.toDto(newAccount);
  }

  /**
   * Функция для получения баланса
   *
   * @param accountId Id нужного аккаунта
   * @return Баланс аккаунта
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public BigDecimal getBalance(long accountId) throws AccountNotFoundException {
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return account.get().getBalance();
  }

  /**
   * Функция для снятия денег с баланса
   *
   * @param accountId Id нужного аккаунта
   * @param amount    Снимаемая суммая
   * @throws NotEnoughMoneyException  Выдаем ошибку, если на счете недостаточно средств
   * @throws NegativeValueException   Выдаем ошибку, если пытаемся снять отрицательные деньги
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public void withdraw(long accountId, BigDecimal amount)
      throws AccountNotFoundException, NotEnoughMoneyException, NegativeValueException {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativeValueException();
    }
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    BigDecimal currentBalance = account.get().getBalance();
    if (currentBalance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
      throw new NotEnoughMoneyException();
    }
    updateInfo(account.get(), account.get().getBalance().subtract(amount), OperationType.Withdraw);
  }

  /**
   * Функция для пополнения денег
   *
   * @param accountId Id нужного аккаунта
   * @param amount    Пополняемая сумма
   * @throws NegativeValueException   Выдаем ошибку, если пытаемся положить отрицательные деньги
   * @throws AccountNotFoundException Выдаем ошибку, если аккаунт не найден
   */
  @Override
  public void deposit(long accountId, BigDecimal amount)
      throws AccountNotFoundException, NegativeValueException {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativeValueException();
    }
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    updateInfo(account.get(), account.get().getBalance().add(amount), OperationType.Deposit);
  }

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
  @Override
  public void transfer(long fromAccountId, long toAccountId, BigDecimal amount)
      throws NegativeValueException, AccountNotFoundException, NotEnoughMoneyException {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new NegativeValueException();
    }
    Optional<Account> accountFrom = accountRepository.findById(fromAccountId);
    Optional<Account> accountTo = accountRepository.findById(toAccountId);
    if (accountFrom.isEmpty() || accountTo.isEmpty()) {
      throw new AccountNotFoundException();
    }

    BigDecimal fromBalance = accountFrom.get().getBalance();
    User userFrom = accountFrom.get().getOwner();
    User userTo = accountTo.get().getOwner();
    if (userTo == userFrom) {
      if (fromBalance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
        throw new NotEnoughMoneyException();
      }
      updateInfo(accountTo.get(), accountTo.get().getBalance().add(amount), OperationType.Transfer);
      updateInfo(accountFrom.get(), accountFrom.get().getBalance().subtract(amount),
          OperationType.Transfer);
      return;
    }
    BigDecimal newAmount;
    if (friendshipRepository.findFriendsByUser(userFrom).contains(userTo.getLogin())) {
      newAmount = amount.multiply(BigDecimal.valueOf(1.03));
    } else {
      newAmount = amount.multiply(BigDecimal.valueOf(1.1));
    }
    if (fromBalance.subtract(newAmount).compareTo(BigDecimal.ZERO) < 0) {
      throw new NotEnoughMoneyException();
    }
    updateInfo(accountFrom.get(), accountFrom.get().getBalance().subtract(newAmount),
        OperationType.Transfer);
    updateInfo(accountTo.get(), accountTo.get().getBalance().add(amount), OperationType.Transfer);
  }

  @Override
  public List<OperationDto> getOperations() {
    return operationRepository.findAll().stream().map(OperationMapping::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<OperationDto> getOperationsByAccountId(Long accountId)
      throws AccountNotFoundException {
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return operationRepository.findOperationsByAccount(account.get()).stream()
        .map(OperationMapping::toDto).collect(Collectors.toList());
  }

  @Override
  public List<OperationDto> getOperationsByType(OperationType type) {
    return operationRepository.findOperationsByOperationType(type).stream()
        .map(OperationMapping::toDto).collect(Collectors.toList());
  }

  @Override
  public List<OperationDto> getOperationsByAccountAndType(Long accountId, OperationType type)
      throws AccountNotFoundException {
    Optional<Account> account = accountRepository.findById(accountId);
    if (account.isEmpty()) {
      throw new AccountNotFoundException();
    }
    return operationRepository.findOperationsByOperationTypeAndAccount(type, account.get()).stream()
        .map(OperationMapping::toDto).collect(Collectors.toList());
  }

  @Override
  public List<OperationDto> getFilteredOperations(Long accountId, OperationType type)
      throws AccountNotFoundException {
    if (accountId == null && type == null) {
      return this.getOperations();
    }
    if (accountId == null) {
      return this.getOperationsByType(type);
    }
    if (type == null) {
      return this.getOperationsByAccountId(accountId);
    }
    return this.getOperationsByAccountAndType(accountId, type);
  }

}

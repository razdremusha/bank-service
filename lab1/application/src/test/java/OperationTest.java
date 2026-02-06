import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.AccountRepository;
import org.example.FriendshipRepository;
import org.example.OperationRepository;
import org.example.UserRepository;
import org.example.entity.Account;
import org.example.entity.FriendPair;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.entity.Operation;
import org.example.entity.User;
import org.example.service.*;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.AlreadyExistException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;
import org.example.service.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.mockito.Mockito;


public class OperationTest {

  @Test
  void shouldThrowNotFoundAccountException() {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    LoginServiceImpl loginService = new LoginServiceImpl(userIdentifier, userRepository);
    String login = "test";
    when(userRepository.findById(login)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> loginService.login(login));

    verify(userRepository, times(1)).findById(login);
  }

  @Test
  void shouldCreateAccount() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;

    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);

    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));

    assertEquals(userRepository.findById(login).get(), newUser);
  }

  @Test
  void shouldThrowAlreadyExistException() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;

    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);

    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));

    int newAge = 20;
    String newName = "katya";
    assertThrows(AlreadyExistException.class,
        () -> userService.createUser(login, newName, newAge, gender, hairColour));
  }

  @Test
  void shouldCreateUserAndAccountSuccessfully() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);

    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    Account account = new Account();
    var money = BigDecimal.valueOf(100);
    account.setBalance(money);
    account.setOwner(newUser);

    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
    accountService.createAccount(BigDecimal.valueOf(100));

    var accounts = new ArrayList<Account>();
    accounts.add(account);
    when(accountRepository.findAccountsByOwner(newUser)).thenReturn(accounts);

    assertEquals(accountRepository.findAccountsByOwner(newUser).get(0).getBalance(),
        BigDecimal.valueOf(100));
  }

  @Test
  void shouldDeposit() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    int startBalance = 100;
    int depositAmount = 100;

    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    Account account = new Account();
    var money = BigDecimal.valueOf(startBalance);
    account.setBalance(money);
    account.setOwner(newUser);
    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);

    accountService.createAccount(BigDecimal.valueOf(startBalance));
    var accounts = new ArrayList<Account>();
    accounts.add(account);
    when(accountRepository.findAccountsByOwner(newUser)).thenReturn(accounts);

    long accountId = 8;
    Mockito.when(operationRepository.save(any(Operation.class))).thenReturn(null);

    when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(account));
    try {
      operationService.deposit(accountId, BigDecimal.valueOf(depositAmount));
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NegativeValueException e) {
      throw new RuntimeException(e);
    }

    account.setBalance(money.add(BigDecimal.valueOf(depositAmount)));
    when(accountRepository.findAccountsByOwner(newUser)).thenReturn(accounts);
    assertEquals(accountRepository.findAccountsByOwner(newUser).get(0).getBalance(),
        BigDecimal.valueOf(startBalance + depositAmount));
  }

  @Test
  void shouldWithdrawNoError() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    int startBalance = 100;
    int withdrawAmount = 20;

    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    Account account = new Account();
    var money = BigDecimal.valueOf(startBalance);
    account.setBalance(money);
    account.setOwner(newUser);
    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);

    accountService.createAccount(BigDecimal.valueOf(startBalance));
    long accountId = 8;

    Mockito.when(operationRepository.save(any(Operation.class))).thenReturn(null);
    when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(account));

    try {
      operationService.withdraw(accountId, BigDecimal.valueOf(withdrawAmount));
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NotEnoughMoneyException e) {
      throw new RuntimeException(e);
    } catch (NegativeValueException e) {
      throw new RuntimeException(e);
    }
    account.setBalance(money.subtract(BigDecimal.valueOf(withdrawAmount)));
    var accounts = new ArrayList<Account>();
    accounts.add(account);
    when(accountRepository.findAccountsByOwner(newUser)).thenReturn(accounts);

    when(accountRepository.findAccountsByOwner(newUser)).thenReturn(accounts);
    assertEquals(accountRepository.findAccountsByOwner(newUser).get(0).getBalance(),
        BigDecimal.valueOf(startBalance - withdrawAmount));
  }

  @Test
  void shouldWithdrawNotEnough() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());

    int startBalance = 100;
    int withdrawAmount = 101;

    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    Account account = new Account();
    var money = BigDecimal.valueOf(startBalance);
    account.setBalance(money);
    account.setOwner(newUser);
    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
    // todo: change save

    accountService.createAccount(BigDecimal.valueOf(startBalance));
    long accountId = 8;
    Mockito.when(operationRepository.save(any(Operation.class))).thenReturn(null);
    when(accountRepository.findById(accountId)).thenReturn(Optional.ofNullable(account));

    assertThrows(NotEnoughMoneyException.class,
        () -> operationService.withdraw(accountId, BigDecimal.valueOf(withdrawAmount)));
  }

  @Test
  void shouldTransferBetweenYours() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    int startBalance = 100;
    int otherAmount = 101;
    int transferAmount = 50;

    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
    accountService.createAccount(BigDecimal.valueOf(startBalance));
    accountService.createAccount(BigDecimal.valueOf(otherAmount));
    long accountIdFrom = 1;
    long accountIdTo = 0;
    Account account = new Account();
    account.setBalance(BigDecimal.valueOf(startBalance));
    account.setOwner(newUser);
    Account otherAccount = new Account();
    otherAccount.setBalance(BigDecimal.valueOf(otherAmount));
    otherAccount.setOwner(newUser);
    when(accountRepository.findById(accountIdFrom)).thenReturn(Optional.ofNullable(account));
    when(accountRepository.findById(accountIdTo)).thenReturn(Optional.ofNullable(otherAccount));

    try {
      operationService.transfer(accountIdFrom, accountIdTo, BigDecimal.valueOf(transferAmount));
    } catch (NegativeValueException e) {
      throw new RuntimeException(e);
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NotEnoughMoneyException e) {
      throw new RuntimeException(e);
    }
    account.setBalance(BigDecimal.valueOf(startBalance - transferAmount));
    otherAccount.setBalance(BigDecimal.valueOf(otherAmount + transferAmount));

    assertEquals(accountRepository.findById(accountIdFrom).get().getBalance(),
        BigDecimal.valueOf(startBalance - transferAmount));
    assertEquals(accountRepository.findById(accountIdTo).get().getBalance(),
        BigDecimal.valueOf(otherAmount + transferAmount));
  }

  @Test
  void shouldTransferBetweenFriends() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    FriendshipServiceImpl friendshipService = new FriendshipServiceImpl(userIdentifier,
        userRepository, friendshipRepository);

    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());

    int startBalance = 100;
    int otherAmount = 101;
    int transferAmount = 50;

    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    User friend = new User();
    String friendLogin = "mur";
    String friendName = "yana";
    int friendAge = 19;
    var friendGender = Gender.Female;
    var friendHairColour = HairColour.Blond;
    friend.setLogin(friendLogin);
    friend.setName(friendName);
    friend.setAge(friendAge);
    friend.setGender(friendGender);
    friend.setHairColor(hairColour);
    Mockito.when(userRepository.findById(friendLogin)).thenReturn(Optional.empty());

    try {
      userService.createUser(friendLogin, friendName, friendAge, friendGender, friendHairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(userRepository.findById(friendLogin)).thenReturn(Optional.ofNullable(friend));

    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
    var userAccount = new Account();
    userAccount.setBalance(BigDecimal.valueOf(startBalance));
    userAccount.setOwner(newUser);

    accountService.createAccount(BigDecimal.valueOf(startBalance));

    try {
      loginService.login(friendLogin);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    var friendAccount = new Account();
    friendAccount.setBalance(BigDecimal.valueOf(otherAmount));
    friendAccount.setOwner(friend);
    accountService.createAccount(BigDecimal.valueOf(otherAmount));

    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }

    when(friendshipRepository.save(any(FriendPair.class))).thenReturn(null);
    try {
      friendshipService.addFriend(friendLogin);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    long accountIdFrom = 0;
    long accountIdTo = 1;

    var listFriends = new ArrayList<User>();
    listFriends.add(friend);
    when(friendshipRepository.findFriendsByUser(newUser)).thenReturn(listFriends);

    when(accountRepository.findById(accountIdFrom)).thenReturn(Optional.ofNullable(userAccount));
    when(accountRepository.findById(accountIdTo)).thenReturn(Optional.ofNullable(friendAccount));
    Mockito.when(operationRepository.save(any(Operation.class))).thenReturn(null);

    try {
      operationService.transfer(accountIdFrom, accountIdTo, BigDecimal.valueOf(transferAmount));
    } catch (NegativeValueException e) {
      throw new RuntimeException(e);
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NotEnoughMoneyException e) {
      throw new RuntimeException(e);
    }

    BigDecimal finalBalance = BigDecimal.valueOf(startBalance)
        .subtract(BigDecimal.valueOf(transferAmount).multiply(BigDecimal.valueOf(1.03)));

    userAccount.setBalance(finalBalance);
    friendAccount.setBalance(BigDecimal.valueOf(otherAmount + transferAmount));
    assertEquals(accountRepository.findById(accountIdFrom).get().getBalance(),
        finalBalance);
    assertEquals(accountRepository.findById(accountIdTo).get().getBalance(),
        BigDecimal.valueOf(otherAmount + transferAmount));
  }

  @Test
  void shouldTransferBetweenNotFriends() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    UserIdentifierImpl userIdentifier = new UserIdentifierImpl();
    UserServiceImpl userService = new UserServiceImpl(userIdentifier, userRepository);
    AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, userRepository,
        userIdentifier);
    OperationRepository operationRepository = Mockito.mock(OperationRepository.class);
    FriendshipRepository friendshipRepository = Mockito.mock(FriendshipRepository.class);
    OperationServiceImpl operationService = new OperationServiceImpl(accountRepository,
        userRepository, operationRepository, friendshipRepository);
    FriendshipServiceImpl friendshipService = new FriendshipServiceImpl(userIdentifier,
        userRepository, friendshipRepository);
    String login = "meow";
    String name = "katenika";
    int age = 19;
    var gender = Gender.Female;
    var hairColour = HairColour.Brown;
    User newUser = new User();
    newUser.setLogin(login);
    newUser.setName(name);
    newUser.setAge(age);
    newUser.setGender(gender);
    newUser.setHairColor(hairColour);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.empty());
    int startBalance = 100;
    int otherAmount = 101;
    int transferAmount = 50;
    try {
      userService.createUser(login, name, age, gender, hairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    var loginService = new LoginServiceImpl(userIdentifier, userRepository);
    Mockito.when(userRepository.findById(login)).thenReturn(Optional.ofNullable(newUser));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(accountRepository.save(any(Account.class))).thenReturn(null);
    var userAccount = new Account();
    userAccount.setBalance(BigDecimal.valueOf(startBalance));
    userAccount.setOwner(newUser);
    accountService.createAccount(BigDecimal.valueOf(startBalance));
    User friend = new User();
    String friendLogin = "mur";
    String friendName = "yana";
    int friendAge = 19;
    var friendGender = Gender.Female;
    var friendHairColour = HairColour.Blond;
    friend.setLogin(friendLogin);
    friend.setName(friendName);
    friend.setAge(friendAge);
    friend.setGender(friendGender);
    friend.setHairColor(hairColour);
    Mockito.when(userRepository.findById(friendLogin)).thenReturn(Optional.empty());
    try {
      userService.createUser(friendLogin, friendName, friendAge, friendGender, friendHairColour);
    } catch (AlreadyExistException e) {
      throw new RuntimeException(e);
    }
    Mockito.when(userRepository.findById(friendLogin)).thenReturn(Optional.ofNullable(friend));
    try {
      loginService.login(friendLogin);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    var friendAccount = new Account();
    friendAccount.setBalance(BigDecimal.valueOf(otherAmount));
    friendAccount.setOwner(friend);
    accountService.createAccount(BigDecimal.valueOf(otherAmount));
    try {
      loginService.login(login);
    } catch (UserNotFoundException e) {
      throw new RuntimeException(e);
    }
    long accountIdFrom = 0;
    long accountIdTo = 1;
    var listFriends = new ArrayList<User>();
    when(friendshipRepository.findFriendsByUser(newUser)).thenReturn(listFriends);
    when(accountRepository.findById(accountIdFrom)).thenReturn(Optional.ofNullable(userAccount));
    when(accountRepository.findById(accountIdTo)).thenReturn(Optional.ofNullable(friendAccount));
    Mockito.when(operationRepository.save(any(Operation.class))).thenReturn(null);

    try {
      operationService.transfer(accountIdFrom, accountIdTo, BigDecimal.valueOf(transferAmount));
    } catch (NegativeValueException e) {
      throw new RuntimeException(e);
    } catch (AccountNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NotEnoughMoneyException e) {
      throw new RuntimeException(e);
    }
    BigDecimal finalBalance = BigDecimal.valueOf(startBalance)
        .subtract(BigDecimal.valueOf(transferAmount).multiply(BigDecimal.valueOf(1.1)));
    userAccount.setBalance(finalBalance);
    friendAccount.setBalance(BigDecimal.valueOf(otherAmount + transferAmount));

    assertEquals(accountRepository.findById(accountIdFrom).get().getBalance(),
        finalBalance);
    assertEquals(accountRepository.findById(accountIdTo).get().getBalance(),
        BigDecimal.valueOf(otherAmount + transferAmount));
  }

}


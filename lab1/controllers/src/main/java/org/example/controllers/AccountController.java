package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.entity.OperationType;
import org.example.service.contracts.AccountService;
import org.example.service.contracts.LoginService;
import org.example.service.contracts.OperationService;
import org.example.service.contracts.UserService;
import org.example.service.dto.AccountDto;
import org.example.service.dto.OperationDto;
import org.example.service.exceptions.AccountNotFoundException;
import org.example.service.exceptions.NegativeValueException;
import org.example.service.exceptions.NotEnoughMoneyException;
import org.example.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(
    name = "Account Controller",
    description = "Control accounts and operations"
)
@RestController
public class AccountController {

  private final AccountService accountService;
  private final OperationService operationService;

  private final LoginService loginService;
  private final UserService userService;

  @Autowired
  public AccountController(AccountService accountService, OperationService operationService,
      LoginService loginService, UserService userService) {
    this.accountService = accountService;
    this.operationService = operationService;
    this.loginService = loginService;
    this.userService = userService;
  }

  @Operation(summary = "Get all operations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operations provided")
  })
  @GetMapping(value = "/operations")
  public ResponseEntity<List<OperationDto>> getOperations(
      @RequestParam(name = "type", required = false) String type,
      @RequestParam(name = "id", required = false) Long accountId) throws AccountNotFoundException {
    var operationType = type != null ? OperationType.valueOf(type) : null;
    var operationDtos = operationService.getFilteredOperations(accountId,
        operationType);
    return new ResponseEntity<>(operationDtos, HttpStatus.OK);
  }

  @Operation(summary = "Create account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Account created"),
      @ApiResponse(responseCode = "404", description = "User not found")
  })
  @PostMapping(value = "/accounts/{owner}/{balance}")
  public ResponseEntity<?> createAccount(@PathVariable("owner") String owner,
      @PathVariable("balance") BigDecimal balance)
      throws UserNotFoundException {
    loginService.login(owner);
    accountService.createAccount(balance);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "Deposit money")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operation successfully completed "),
      @ApiResponse(responseCode = "404", description = "Account not found"),
      @ApiResponse(responseCode = "400", description = "Deposit negative value")
  })
  @PutMapping(value = "/accounts/deposit/{accountId}/{amount}")
  public ResponseEntity<?> deposit(@PathVariable("accountId") Long accountId,
      @PathVariable("amount") BigDecimal amount)
      throws NegativeValueException, AccountNotFoundException {
    operationService.deposit(accountId, amount);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Withdraw money")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operation successfully completed "),
      @ApiResponse(responseCode = "404", description = "Account not found"),
      @ApiResponse(responseCode = "400", description = "Deposit negative value"),
      @ApiResponse(responseCode = "409", description = "Not enough money on account")
  })
  @PutMapping(value = "/accounts/withdraw/{accountId}/{amount}")
  public ResponseEntity<?> withdraw(@PathVariable("accountId") Long accountId,
      @PathVariable("amount") BigDecimal amount)
      throws NegativeValueException, NotEnoughMoneyException, AccountNotFoundException {
    operationService.withdraw(accountId, amount);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Transfer money")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Operation successfully completed "),
      @ApiResponse(responseCode = "404", description = "Account not found"),
      @ApiResponse(responseCode = "400", description = "Deposit negative value"),
      @ApiResponse(responseCode = "409", description = "Not enough money on account")
  })
  @PutMapping(value = "/accounts/transfer/{accountIdFrom}/{accountIdTo}/{amount}")
  public ResponseEntity<?> transfer(@PathVariable("accountIdFrom") Long accountIdFrom,
      @PathVariable("accountIdTo") Long accountIdTo,
      @PathVariable("amount") BigDecimal amount)
      throws NegativeValueException, NotEnoughMoneyException, AccountNotFoundException {
    operationService.transfer(accountIdFrom, accountIdTo, amount);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "Get balance")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Balance successfully received"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  @GetMapping(value = "/accounts/balance/{accountId}")
  public ResponseEntity<BigDecimal> getBalance(@PathVariable("accountId") Long accountId)
      throws AccountNotFoundException {
    return new ResponseEntity<>(operationService.getBalance(accountId), HttpStatus.OK);
  }


  @GetMapping(value = "/accounts")
  public ResponseEntity<List<AccountDto>> getAllAccounts()
      throws AccountNotFoundException {
    return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
  }

  @GetMapping(value = "accounts/login/{login}")
  public ResponseEntity<List<AccountDto>> getAccountsByLogin(@PathVariable("login") String login) throws UserNotFoundException {
    var user = userService.findByLogin(login);
    if ( user.isEmpty() )
      throw new UserNotFoundException();

    return new ResponseEntity<>(accountService.getAccountsByOwner(login), HttpStatus.OK);
  }

  @GetMapping(value = "accounts/{id}")
  public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") long id)
      throws UserNotFoundException, AccountNotFoundException {
    return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
  }



}
package org.example.controllers;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.service.dto.AccountDto;
import org.example.service.dto.AccountWithHistoryDto;
import org.example.services.AccountWebService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountWebService accountWebService = new AccountWebService();

  @GetMapping(value = "/accounts/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_CLIENT','ROLE_ADMIN')")
  @PostAuthorize("hasAuthority('ROLE_ADMIN') or ((hasAuthority('ROLE_CLIENT') and returnObject.body.loginOwner() == authentication.name))")
  public ResponseEntity<AccountDto> getAccountsById(@PathVariable("id") @P("id") Long id) {
    return accountWebService.getAccountById(id);
  }

  @GetMapping(value = "/accounts/login/{login}")
  @PreAuthorize("hasAnyAuthority('ROLE_CLIENT','ROLE_ADMIN')")
  @PostAuthorize("hasAuthority('ROLE_ADMIN') or (hasAuthority('ROLE_CLIENT') and #login == authentication.name)")
  public ResponseEntity<List<AccountDto>> getAllAccountsByLogin(
      @PathVariable("login") @P("login") String login) {
    return accountWebService.getAccountsByLogin(login);
  }

  @GetMapping(value = "/accounts")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<AccountDto>> getAllAccounts() {
    return accountWebService.getAllAccounts();
  }

  @GetMapping(value = "/accounts/{id}/history")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<AccountWithHistoryDto> getAccountWithHistory(@PathVariable("id") Long id) {
    return accountWebService.getAccountWithHistory(id);
  }

  @PutMapping(value = "/operations/deposit/{accountId}/{amount}")
  @PreAuthorize("hasAuthority('ROLE_CLIENT')")
  public ResponseEntity deposit(@PathVariable("accountId") Long accountId,
      @PathVariable("amount") BigDecimal amount) {
    return accountWebService.deposit(accountId, amount);
  }

  @PutMapping(value = "/operations/withdraw/{accountId}/{amount}")
  @PreAuthorize("hasAuthority('ROLE_CLIENT')")
  public ResponseEntity withdraw(@PathVariable("accountId") Long accountId,
      @PathVariable("amount") BigDecimal amount) {
    return accountWebService.withdraw(accountId, amount);
  }

  @PutMapping(value = "/operations/transfer/{accountFrom}/{accountTo}/{amount}")
  @PreAuthorize("hasAuthority('ROLE_CLIENT')")
  public ResponseEntity transfer(@PathVariable("accountFrom") Long accountFrom,
      @PathVariable("accountTo") Long accountTo,
      @PathVariable("amount") BigDecimal amount) {
    return accountWebService.transfer(accountFrom, accountTo, amount);
  }


}

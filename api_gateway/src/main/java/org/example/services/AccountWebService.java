package org.example.services;

import java.math.BigDecimal;
import java.util.List;
import org.example.service.dto.AccountDto;
import org.example.service.dto.AccountWithHistoryDto;
import org.example.service.dto.OperationDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Service
public class AccountWebService {

  final private RestClient restClient = RestClient.create();

  final private String url = "http://localhost:8080";

  public ResponseEntity getAccountById(long id) {
    String newUrl = url + "/accounts/" + id;
    try {
      return restClient.get().uri(newUrl).retrieve().toEntity(AccountDto.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity getAccountsByLogin(String login) {
    String newUrl = url + "/accounts/login/" + login;
    try {
      return restClient.get().uri(newUrl).retrieve()
          .toEntity(new ParameterizedTypeReference<List<AccountDto>>() {
          });
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity getAllAccounts() {
    String newUrl = url + "/accounts";
    try {
      return restClient.get().uri(newUrl).retrieve()
          .toEntity(new ParameterizedTypeReference<List<AccountDto>>() {
          });
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity getAccountWithHistory(long id) {
    String urlAccount = url + "/accounts/" + id;
    String urlHistory = url + "/operations?id=" + id;
    try {
      var account = restClient.get()
          .uri(urlAccount)
          .retrieve()
          .body(AccountDto.class);

      var history = restClient.get()
          .uri(urlHistory)
          .retrieve()
          .body(new ParameterizedTypeReference<List<OperationDto>>() {
          });

      AccountWithHistoryDto accountWithHistoryDto = new AccountWithHistoryDto(
          account.accountId(),
          account.loginOwner(),
          account.balance(),
          history);

      return new ResponseEntity<>(accountWithHistoryDto, HttpStatus.OK);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity deposit(long id, BigDecimal amount) {
    String newUrl = url + "/accounts/deposit/" + id + "/" + amount;
    try {
      return restClient.put().uri(newUrl).retrieve().toEntity(Void.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity withdraw(long id, BigDecimal amount) {
    String newUrl = url + "/accounts/withdraw/" + id + "/" + amount;
    try {
      return restClient.put().uri(newUrl).retrieve().toEntity(Void.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }

  public ResponseEntity transfer(long idFrom, long idTo, BigDecimal amount) {
    String newUrl = url + "/accounts/transfer/" + idFrom + "/" + idTo + "/" + amount;
    try {
      return restClient.put().uri(newUrl).retrieve().toEntity(Void.class);
    } catch (HttpStatusCodeException ex) {
      return ResponseEntity
          .status(ex.getStatusCode())
          .headers(ex.getResponseHeaders())
          .body(ex.getResponseBodyAsString());
    }
  }
}

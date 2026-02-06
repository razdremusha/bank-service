package org.example.service.dto;

import java.util.List;
import org.example.entity.Account;

public class AccountMapping {

  public static AccountDto toDto(Account account) {
    if (account == null) {
      return null;
    }
    return new AccountDto(account.getId(), account.getOwner().getLogin(), account.getBalance());
  }

  public static AccountWithHistoryDto toHistoryDto(Account account, List<OperationDto> history) {
    if (account == null) {
      return null;
    }
    return new AccountWithHistoryDto(account.getId(), account.getOwner().getLogin(),
        account.getBalance(), history);
  }
}

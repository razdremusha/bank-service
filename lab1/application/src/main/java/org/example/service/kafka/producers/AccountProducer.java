package org.example.service.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.AccountDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountProducer {
  private final KafkaTemplate<String, AccountDto> accountKafkaTemplate;

  public AccountProducer(KafkaTemplate<String, AccountDto> accountKafkaTemplate) {
    this.accountKafkaTemplate = accountKafkaTemplate;
  }

  public void sendAccountMessage(AccountDto account) {
    log.info("Sending Json Serializer : {}", account);
    log.info("--------------------------------");
    accountKafkaTemplate.send("account-topic", account.accountId().toString(), account);
  }
}

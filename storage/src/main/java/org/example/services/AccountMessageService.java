package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountDto;
import org.example.dto.ClientDto;
import org.example.infrastructure.AccountRepository;
import org.example.infrastructure.ClientRepository;
import org.example.infrastructure.entities.Account;
import org.example.infrastructure.entities.Client;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountMessageService {
  private final String topic= "account-topic";
  private final String kafkaConsumerGroupId = "account";

  private final AccountRepository accountRepository;

  private final ModelMapper modelMapper = new ModelMapper();

  @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId)
  public void getMessage(AccountDto accountDto) {
    accountRepository.save(modelMapper.map(accountDto, Account.class));
  }
}

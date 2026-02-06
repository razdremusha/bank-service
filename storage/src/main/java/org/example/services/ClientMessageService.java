package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.infrastructure.entities.Client;
import org.example.infrastructure.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientMessageService {
  private final String topic= "client-topic";
  private final String kafkaConsumerGroupId = "client";

  private final ClientRepository clientRepository;

  private final ModelMapper modelMapper = new ModelMapper();

  @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId)
  public void getMessage(ClientDto clientDto) {
    clientRepository.save(modelMapper.map(clientDto, Client.class));
  }
}

package org.example.service.kafka.producers;

import lombok.extern.slf4j.Slf4j;
import org.example.service.dto.UserDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserProducer {

  private final KafkaTemplate<String, UserDto> userKafkaTemplate;

  public UserProducer(KafkaTemplate<String, UserDto> userKafkaTemplate) {
    this.userKafkaTemplate = userKafkaTemplate;
  }

  public void sendUserMessage(UserDto user) {
    log.info("Sending Json Serializer : {}", user);
    log.info("--------------------------------");
    userKafkaTemplate.send("client-topic", user.login(),user);
  }
}
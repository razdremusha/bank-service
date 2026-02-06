package org.example.service.kafka;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.service.dto.UserDto;
import org.example.service.kafka.producers.UserProducer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserSendingAspect {

  private final UserProducer userProducer;

  public UserSendingAspect(UserProducer userProducer) {
    this.userProducer = userProducer;
  }

  @AfterReturning(pointcut = "@annotation(UserMutable)", returning = "result")
  public void sendAfter(JoinPoint joinPoint, Object result)
  {
    userProducer.sendUserMessage((UserDto) result);
  }

}

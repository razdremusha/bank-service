package org.example.service.kafka;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.AccountRepository;
import org.example.service.dto.AccountDto;
import org.example.service.dto.UserDto;
import org.example.service.kafka.producers.AccountProducer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccountSendingAspect {
  private final AccountProducer accountProducer;

  public AccountSendingAspect(AccountProducer accountProducer) {
    this.accountProducer = accountProducer;
  }

  @AfterReturning(pointcut = "@annotation(AccountMutable)", returning = "result")
  public void sendAfter(JoinPoint joinPoint, Object result)
  {
    accountProducer.sendAccountMessage((AccountDto) result);
  }
}

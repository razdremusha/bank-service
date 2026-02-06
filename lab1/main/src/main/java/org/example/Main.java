package org.example;

import java.util.List;
import java.util.stream.Collectors;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.service.OperationServiceImpl;
import org.example.service.UserServiceImpl;
import org.example.service.contracts.OperationService;
import org.example.service.dto.UserDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    var context = SpringApplication.run(Main.class, args);
  }
}
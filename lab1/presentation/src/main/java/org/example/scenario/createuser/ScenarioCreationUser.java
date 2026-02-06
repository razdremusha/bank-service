package org.example.scenario.createuser;

import org.example.Scenario;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.service.contracts.UserService;
import org.example.service.exceptions.AlreadyExistException;

/**
 * Класс сценария создания пользователя
 */
public class ScenarioCreationUser implements Scenario {

  /**
   * Название сценария
   */
  private final String name = "Create user";

  /**
   * Сервис для работы с юзерами
   */
  private final UserService userService;

  /**
   * Конструктор сценария создания пользователя
   *
   * @param userService Сервис для работы с юзерами
   */
  public ScenarioCreationUser(UserService userService) {
    this.userService = userService;
  }

  /**
   * Функция получает имя сценария
   *
   * @return Имя
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Функция, приводящая сценарий в работу
   */
  @Override
  public void run() {
    System.out.print("Enter login: ");
    String login = System.console().readLine();
    System.out.print("Enter name: ");
    String name = System.console().readLine();
    System.out.print("Enter age: ");
    int age = Integer.parseInt(System.console().readLine());
    System.out.print(
        "Enter hair colour (Blond, Ginger, Grey, Brown), для этого напишите ее порядковый номер: ");
    String color = System.console().readLine();
    HairColour hair = HairColour.valueOf(color);
    System.out.print("Choose gender (Female, Male):");
    String s = System.console().readLine();
    Gender gender = Gender.valueOf(s);
    try {
      userService.createUser(login, name, age, gender, hair);
      System.out.print("User created!");
    } catch (AlreadyExistException e) {
      System.out.print("Whoops! User with such ID already exists!");
    }
  }
}

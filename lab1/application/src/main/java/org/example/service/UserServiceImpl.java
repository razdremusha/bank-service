package org.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.UserRepository;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.entity.User;
import org.example.service.contracts.UserIdentifier;
import org.example.service.contracts.UserService;
import org.example.service.dto.UserDto;
import org.example.service.dto.UserMapping;
import org.example.service.exceptions.AlreadyExistException;
import org.example.service.kafka.UserMutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Класс для взаимодействия с юзерами
 */
@Service
public class UserServiceImpl implements UserService {

  /**
   * Идентификатор юзера
   */

  private final UserIdentifier userIdentifier;
  /**
   * Репозиторий юзеров
   */
  private final UserRepository userRepository;

  /**
   * Конструктор сервиса юзера
   *
   * @param userIdentifier Идентификатор юзера
   * @param userRepository Репозиторий юзеров
   */
  @Autowired
  public UserServiceImpl(UserIdentifier userIdentifier, UserRepository userRepository) {
    this.userIdentifier = userIdentifier;
    this.userRepository = userRepository;
  }

  /**
   * Функция для создания юзера
   *
   * @param login      Логин юзера
   * @param name       Имя юзера
   * @param age        Возраст юзера
   * @param gender     Гендер юзера
   * @param hairColour Цвет волос юзера
   * @return Созданного юзера
   * @throws AlreadyExistException Возвращаем ошибку, если юзер есть
   */
  @Override
  @UserMutable
  public UserDto createUser(String login, String name, int age, Gender gender,
      HairColour hairColour)
      throws AlreadyExistException {
    var result = userRepository.findById(login);
    if (result.isPresent()) {
      throw new AlreadyExistException("user");
    }
    User user = new User();
    user.setName(name);
    user.setAge(age);
    user.setLogin(login);
    user.setGender(gender);
    user.setHairColor(hairColour);
    userRepository.save(user);
    return UserMapping.toDto(user);
  }

  /**
   * Функция для показания информации о юзере
   */
  @Override
  public String showInfo() {
    var result = userRepository.findById(userIdentifier.getUserLogin());
    return "Name:" + result.get().getName() + " Age:" + result.get().getAge() + " Gender:"
        + result.get().getGender() + " HairColour:" + result.get().getHairColor();
  }

  /**
   * Функция для получения юзера по его логину
   *
   * @param login Логин юзера
   * @return Найденного юзера
   */
  @Override
  public Optional<UserDto> findByLogin(String login) {
    return userRepository.findById(login).map(UserMapping::toDto);
  }

  @Override
  public List<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(UserMapping::toDto).collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUsersByGender(Gender gender) {
    return userRepository.findUsersByGender(gender).stream().map(UserMapping::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUsersByHairColour(HairColour hairColour) {
    return userRepository.findUsersByHairColor(hairColour).stream().map(UserMapping::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getUsersByHairColourAndGender(HairColour hairColour, Gender gender) {
    return userRepository.findUsersByHairColorAndGender(hairColour, gender).stream()
        .map(UserMapping::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDto> getFilteredUsers(Gender gender, HairColour hairColour) {
    if (gender == null && hairColour == null) {
      return getAllUsers();
    }
    if (gender == null) {
      return getUsersByHairColour(hairColour);
    }
    if (hairColour == null) {
      return getUsersByGender(gender);
    }
    return getUsersByHairColourAndGender(hairColour, gender);
  }
}

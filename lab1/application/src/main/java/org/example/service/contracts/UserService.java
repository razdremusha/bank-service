package org.example.service.contracts;

import java.util.List;
import java.util.Optional;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.service.dto.UserDto;
import org.example.service.exceptions.AlreadyExistException;

/**
 * Интерфейс для взаимодействия с юзерами
 */
public interface UserService {

    /**
     * Функция для создания юзера
     * @param login Логин юзера
     * @param name Имя юзера
     * @param age Возраст юзера
     * @param gender Гендер юзера
     * @param hairColour Цвет волос юзера
     * @return Созданного юзера
     * @throws AlreadyExistException Возвращаем ошибку, если юзер есть
     */
    UserDto createUser(String login,
                    String name,
                    int age,
                    Gender gender,
                    HairColour hairColour) throws AlreadyExistException;

    /**
     * Функция для показания информации о юзере
     */
    String showInfo();

    /**
     * Функция для получения юзера по его логину
     * @param login Логин юзера
     * @return Найденного юзера
     */
    Optional<UserDto> findByLogin(String login);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersByGender(Gender gender);

    List<UserDto> getUsersByHairColour(HairColour hairColour);

    List<UserDto> getUsersByHairColourAndGender(HairColour hairColour, Gender gender);

    List<UserDto> getFilteredUsers(Gender gender, HairColour hairColour);

}


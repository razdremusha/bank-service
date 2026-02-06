package org.example.service.dto;

import org.example.entity.User;

public class UserMapping {

  public static UserDto toDto(User user) {
    if (user == null) {
      return null;
    }
    return new UserDto(user.getLogin(), user.getName(), user.getAge(), user.getGender(),
        user.getHairColor());
  }
}
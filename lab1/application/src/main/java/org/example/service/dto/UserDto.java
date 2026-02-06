package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.Gender;
import org.example.entity.HairColour;

@Schema(description = "Сущность пользователя")
public record UserDto(String login, String name, int age, Gender gender, HairColour hairColour) {

}

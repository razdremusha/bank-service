package org.example.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.example.infrastructure.entities.Role;

/**
 * DTO for {@link org.example.infrastructure.entities.Client}
 */
@Data
public class ClientDto implements Serializable {

  @NotNull
  String login;
  @NotNull
  String password;
  @NotNull
  String role;
}
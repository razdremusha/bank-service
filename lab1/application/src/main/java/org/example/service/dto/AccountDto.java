package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Сущность счета")
public record AccountDto(Long accountId, String loginOwner, BigDecimal balance) {

}

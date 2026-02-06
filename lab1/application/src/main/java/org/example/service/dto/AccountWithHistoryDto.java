package org.example.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record AccountWithHistoryDto(long id, String ownerId, BigDecimal balance, List<OperationDto> history) {
}

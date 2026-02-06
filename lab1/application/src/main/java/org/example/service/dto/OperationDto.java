package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.example.entity.OperationType;

@Schema(description = "Сущность операции")
public record OperationDto(Long id, Long accountId, BigDecimal difference,
                           OperationType operationType) {

}
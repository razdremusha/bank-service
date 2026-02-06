package org.example.service.dto;

import org.example.entity.Operation;

public class OperationMapping {

  public static OperationDto toDto(Operation operation) {
    if (operation == null) {
      return null;
    }
    return new OperationDto(operation.getId(), operation.getAccount().getId(),
        operation.getDifference(),
        operation.getOperationType());
  }

}

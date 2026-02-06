package org.example;

import java.util.List;
import org.example.entity.Account;
import org.example.entity.Operation;
import org.example.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {


  List<Operation> findOperationsByAccount(Account account);

  List<Operation> findOperationsByOperationType(OperationType operationType);

  List<Operation> findOperationsByOperationTypeAndAccount(OperationType operationType, Account account);
}

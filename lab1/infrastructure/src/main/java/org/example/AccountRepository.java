package org.example;

import java.util.List;
import org.example.entity.Account;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findAccountsByOwner(User owner);


}
package org.example.infrastructure;

import java.util.Optional;
import org.example.infrastructure.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByLogin(String login);
}

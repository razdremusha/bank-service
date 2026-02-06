package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.AlreadyExist;
import org.example.infrastructure.ClientsRepository;
import org.example.infrastructure.entities.Client;
import org.example.infrastructure.entities.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  final private ClientsRepository repository;
  private final PasswordEncoder passwordEncoder;

  public Client createClient(String login, String password, Role role) throws AlreadyExist {
    if (repository.findByLogin(login).isPresent()) {
      throw new AlreadyExist("client with login:" + login + " already exist");
    }
    Client client = new Client();
    client.setLogin(login);
    client.setPassword(passwordEncoder.encode(password));
    client.setRole(role);

    return repository.save(client);
  }

}

package org.example.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.infrastructure.ClientsRepository;
import org.example.infrastructure.entities.Client;
import org.example.utils.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

  private final ClientsRepository clientsRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Client> client = clientsRepository.findByLogin(username);
    if (client.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }
    return new ClientDetails(client.get());
  }
}

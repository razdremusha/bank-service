package org.example.utils;

import java.util.Collection;
import java.util.List;
import org.example.infrastructure.entities.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ClientDetails implements UserDetails {

  private final Client client;

  public ClientDetails(Client client) {
    this.client = client;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //TODO if I would not be dead after all code, change this nonsense to permissions
    return List.of(new SimpleGrantedAuthority(client.getRole().toString()));
  }

  @Override
  public String getPassword() {
    return client.getPassword();
  }

  @Override
  public String getUsername() {
    return client.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

package org.example.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "account_id", nullable = false)
  private Long accountId;

  @NotNull
  @Column(name = "login_owner", nullable = false, length = Integer.MAX_VALUE)
  private String loginOwner;

  @NotNull
  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

}
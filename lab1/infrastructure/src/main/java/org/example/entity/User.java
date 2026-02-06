package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(name = "users_id_gen", sequenceName = "operations_id_seq", allocationSize = 1)
  @Column(name = "login", nullable = false, length = 100)
  private String login;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "age")
  private Integer age;
  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "hair_color")
  private HairColour hairColor;
}
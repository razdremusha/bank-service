package org.example;

import java.util.List;
import org.example.entity.Gender;
import org.example.entity.HairColour;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  List<User> findUsersByGender(Gender gender);

  List<User> findUsersByHairColor(HairColour hairColour);

  List<User> findUsersByHairColorAndGender(HairColour hairColour, Gender gender);
}
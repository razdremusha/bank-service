package org.example;

import java.util.List;
import org.example.entity.FriendPair;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendPair, Long> {

  @Query("SELECT fp.friend FROM FriendPair fp WHERE fp.user = :user")
  List<User> findFriendsByUser(@Param("user") User user);
}
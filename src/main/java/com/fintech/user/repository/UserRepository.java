package com.fintech.user.repository;

import com.fintech.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("select u from User u where u.email = :email")
  User findByEmail(String email);
}

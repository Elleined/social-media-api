package com.elleined.socialmediaapi.repository.user;

import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUUID(String UUID);
}
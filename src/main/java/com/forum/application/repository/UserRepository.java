package com.forum.application.repository;

import com.forum.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    int fetchIdByEmail(@Param("email") String email);

    @Query("SELECT u.email FROM User u")
    List<String> fetchAllEmail();

    @Query("SELECT u FROM User u WHERE u.name LIKE CONCAT(:name, '%')")
    List<User> fetchAllByProperty(@Param("name") String name);
}
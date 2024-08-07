package com.elleined.socialmediaapi.repository.user;

import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name LIKE CONCAT('%', :name, '%')")
    Page<User> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT u.blockedUsers FROM User u WHERE u = :currentUser")
    Page<User> findAllBlockedUsers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followers FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followings FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowings(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.friends FROM User u WHERE u = :currentUser")
    Page<User> findAllFriends(@Param("currentUser") User currentUser, Pageable pageable);
}
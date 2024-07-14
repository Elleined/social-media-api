package com.elleined.socialmediaapi.repository.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query("""
            SELECT fr
            FROM FriendRequest fr
            WHERE fr.requestedUser = :currentUser
            """)
    Page<FriendRequest> findAll(@Param("currentUser") User currentUser, Pageable pageable);
}
package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
}
package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.friend.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
}
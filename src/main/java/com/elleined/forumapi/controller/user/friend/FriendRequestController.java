package com.elleined.forumapi.controller.user.friend;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/friend-requests")
public class FriendRequestController {
    @GetMapping
    public Set<FriendRequest> getAllFriendRequests(User currentUser) {
        return null;
    }

    @PostMapping
    public void sendFriendRequest(User currentUser, User userToAdd) {

    }

    @PatchMapping("/{friendRequestId}/accept")
    public void acceptFriendRequest(User currentUser, int friendRequestId) {

    }
}

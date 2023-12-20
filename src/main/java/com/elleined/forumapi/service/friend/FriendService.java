package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;

import java.util.List;
import java.util.Set;

public interface FriendService {

    // Current user alias for Requested User
    void acceptFriendRequest(User currentUser, int friendRequestId);

    void sendFriendRequest(User currentUser, User userToAdd);
    void unFriend(User currentUser, User userToUnFriend);

    Set<User> getAllFriends(User currentUser);
    Set<FriendRequest> getAllFriendRequests(User currentUser);
}

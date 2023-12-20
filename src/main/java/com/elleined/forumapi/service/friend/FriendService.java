package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.model.User;

import java.util.List;

public interface FriendService {

    // Current user alias for Requested User
    void acceptFriendRequest(User currentUser, int friendRequestId);

    void sendFriendRequest(User currentUser, User userToAdd);
    void unFriend(User currentUser, User userToUnFriend);

    List<User> getAllFriends(User currentUser);
    List<User> getAllFriendRequests(User currentUser);
}

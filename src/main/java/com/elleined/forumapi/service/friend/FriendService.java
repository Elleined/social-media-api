package com.elleined.forumapi.service.friend;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;

import java.util.List;
import java.util.Set;

public interface FriendService {

    // Current user alias for Requested User
    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);
    void deleteFriendRequest(User currentUser, FriendRequest friendRequest);

    void sendFriendRequest(User currentUser, User userToAdd);

    void unFriend(User currentUser, User userToUnFriend);

    Set<User> getAllFriends(User currentUser);
    List<FriendRequest> getAllFriendRequests(User currentUser);

    FriendRequest getById(int id) throws ResourceNotFoundException;
}

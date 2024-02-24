package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;

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

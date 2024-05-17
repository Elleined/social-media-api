package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;

public interface FriendService {
    FriendRequest getById(int id) throws ResourceNotFoundException;
    Set<User> getAllFriends(User currentUser);
    List<FriendRequest> getAllFriendRequests(User currentUser);

    void acceptFriendRequest(User currentUser, FriendRequest friendRequest);
    void deleteFriendRequest(User currentUser, FriendRequest friendRequest);

    void sendFriendRequest(User currentUser, User userToAdd);

    void unFriend(User currentUser, User userToUnFriend);
}

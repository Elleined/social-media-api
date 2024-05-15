package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.user.User;
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


    public boolean isFriendsWith(User anotherUser) {
        return this.getFriends().contains(anotherUser);
    }
    public boolean hasFriendRequest(FriendRequest friendRequest) {
        return this.getReceiveFriendRequests().contains(friendRequest);
    }
    public boolean hasAlreadySentFriendRequestTo(User userToAdd) {
        return this.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(userToAdd::equals);
    }

    public boolean hasAlreadyReceiveFriendRequestTo(User userToAdd) {
        return userToAdd.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(this::equals);
    }
}

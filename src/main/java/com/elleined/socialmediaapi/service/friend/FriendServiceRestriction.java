package com.elleined.socialmediaapi.service.friend;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;

public interface FriendServiceRestriction {

    default boolean isFriendsWith(User currentUser, User anotherUser) {
        return currentUser.getFriends().contains(anotherUser);
    }

    default boolean notFriendsWith(User currentUser, User anotherUser) {
        return !isFriendsWith(currentUser, anotherUser);
    }

    default boolean hasSendFriendRequest(User currentUser, User userToAdd) {
        return currentUser.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(userToAdd::equals);
    }

    default boolean hasReceiveFriendRequest(User currentUser, User userToAdd) {
        return userToAdd.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(currentUser::equals);
    }

    default boolean hasNotReceiveFriendRequest(User currentUser, User userToAdd) {
        return !hasReceiveFriendRequest(currentUser, userToAdd);
    }

    default boolean isSendingToHimself(User currentUser, User userToAdd) {
        return currentUser.getId() == userToAdd.getId();
    }
}

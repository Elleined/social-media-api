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

    default boolean hasSendFriendRequestTo(User currentUser, User userToAdd) {
        return currentUser.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(userToAdd::equals);
    }

    default boolean hasNotSendFriendRequestTo(User currentUser, User userToAdd) {
        return !hasSendFriendRequestTo(currentUser, userToAdd);
    }

    default boolean hasReceiveFriendRequestTo(User currentUser, User userToAdd) {
        return userToAdd.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(currentUser::equals);
    }
}

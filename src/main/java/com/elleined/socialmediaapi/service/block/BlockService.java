package com.elleined.socialmediaapi.service.block;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.model.user.User;

import java.util.Set;

public interface BlockService {
    void blockUser(User currentUser, User userToBeBlocked) throws BlockedException;

    void unBlockUser(User currentUser, User userToBeUnblocked);

    default boolean isBlockedByYou(User currentUser, User userToCheck) {
        return currentUser.getBlockedUsers().contains(userToCheck);
    }

    default boolean isYouBeenBlockedBy(User currentUser, User suspectedUser) {
        return suspectedUser.getBlockedUsers().contains(currentUser);
    }

    default Set<User> getAllBlockedUsers(User currentUser) {
        return currentUser.getBlockedUsers();
    }
}

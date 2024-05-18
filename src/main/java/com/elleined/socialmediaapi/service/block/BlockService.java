package com.elleined.socialmediaapi.service.block;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.model.user.User;

import java.util.Set;

public interface BlockService {
    void blockUser(User currentUser, User userToBeBlocked) throws BlockedException;

    void unBlockUser(User currentUser, User userToBeUnblocked);

    boolean isBlockedByYou(User currentUser, User userToCheck);

    boolean isYouBeenBlockedBy(User currentUser, User suspectedUser);

    Set<User> getAllBlockedUsers(User currentUser);
}

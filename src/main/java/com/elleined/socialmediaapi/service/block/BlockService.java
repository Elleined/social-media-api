package com.elleined.socialmediaapi.service.block;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.model.User;

import java.util.Set;

public interface BlockService {
    void blockUser(User currentUser, User userToBeBlocked) throws BlockedException;

    void unBlockUser(User currentUser, User userToBeUnblocked);

    boolean isBlockedBy(User currentUser, User userToCheck);

    boolean isYouBeenBlockedBy(User currentUser, User suspectedUser);

    Set<User> getAllBlockedUsers(User currentUser);
}

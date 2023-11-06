package com.elleined.forumapi.service.block;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.model.User;

import java.util.Set;

public interface BlockService {
    void blockUser(User currentUser, User userToBeBlocked) throws BlockedException;

    void unBlockUser(User currentUser, User userToBeUnblocked);

    boolean isBlockedBy(User currentUser, User userToCheck);

    boolean isYouBeenBlockedBy(User currentUser, User suspectedUser);

    Set<User> getAllBlockedUsers(User currentUser);
}

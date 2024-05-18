package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.model.user.User;

public interface FollowServiceRestriction {

    default boolean alreadyFollows(User currentUser, User userToFollow) {
        return currentUser.getFollowings().contains(userToFollow);
    }

    default boolean notFollows(User currentUser, User userToFollow) {
        return !currentUser.getFollowings().contains(userToFollow);
    }
}

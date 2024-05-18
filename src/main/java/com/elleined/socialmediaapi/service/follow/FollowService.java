package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.model.user.User;

import java.util.Set;

public interface FollowService extends FollowServiceRestriction {
    void follow(User currentUser, User userToFollow);
    void unFollow(User currentUser, User userToUnFollow);

    Set<User> getAllFollowers(User currentUser);
    Set<User> getAllFollowing(User currentUser);

    @Override
    default boolean alreadyFollows(User currentUser, User userToFollow) {
        return this.getAllFollowing(currentUser).contains(userToFollow);
    }
}

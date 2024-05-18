package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.model.user.User;

public interface FollowServiceRestriction {
    boolean alreadyFollows(User currentUser, User userToFollow);
}

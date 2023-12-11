package com.elleined.forumapi.service.follow;

import com.elleined.forumapi.model.User;

import java.util.List;
import java.util.Set;

public interface FollowService {
    void follow(User currentUser, User userToFollow);
    void unFollow(User currentUser, User userToUnFollow);

    Set<User> getAllFollowers(User currentUser);
    Set<User> getAllFollowing(User currentUser);
}

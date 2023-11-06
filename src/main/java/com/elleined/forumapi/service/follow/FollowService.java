package com.elleined.forumapi.service.follow;

import com.elleined.forumapi.model.User;

import java.util.List;

public interface FollowService {
    void follow(User currentUser, User userToFollow);
    void unFollow(User currentUser, User userToUnFollow);

    List<User> getAllFollowers(User currentUser);
    List<User> getAllFollowing(User currentUser);
}

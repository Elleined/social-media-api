package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowService {
    void follow(User currentUser, User userToFollow);
    void unFollow(User currentUser, User userToUnFollow);

    List<User> getAllFollowers(User currentUser, Pageable pageable);
    List<User> getAllFollowing(User currentUser, Pageable pageable);
}

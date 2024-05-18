package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
    void unSharePost(User currentUser, Post postToUnShare);

    List<Post> getAllSharedPosts(User currentUser);

    default boolean alreadyShare(User currentUser, Post postToShare) {
        return currentUser.getSharedPosts().contains(postToShare);
    }

    default boolean notShare(User currentUser, Post postToShare) {
        return !this.alreadyShare(currentUser, postToShare);
    }
}

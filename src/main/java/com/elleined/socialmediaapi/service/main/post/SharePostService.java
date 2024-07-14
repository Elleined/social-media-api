package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
    void unSharePost(User currentUser, Post postToUnShare);

    Page<Post> getAllSharedPosts(User currentUser, Pageable pageable);

    default boolean alreadyShare(User currentUser, Post postToShare) {
        return currentUser.getSharedPosts().contains(postToShare);
    }

    default boolean notShare(User currentUser, Post postToShare) {
        return !this.alreadyShare(currentUser, postToShare);
    }
}

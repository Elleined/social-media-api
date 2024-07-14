package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SavedPostService {
    Post savedPost(User currentUser, Post postToSaved);
    void unSavedPost(User currentUser, Post postToUnSave);
    Page<Post> getAllSavedPosts(User currentUser, Pageable pageable);

    default boolean alreadySaved(User currentUser, Post postToSaved) {
        return currentUser.getSavedPosts().contains(postToSaved);
    }

    default boolean notSaved(User currentUser, Post postToSaved) {
        return !this.alreadySaved(currentUser, postToSaved);
    }
}

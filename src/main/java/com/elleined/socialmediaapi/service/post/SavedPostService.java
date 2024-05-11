package com.elleined.socialmediaapi.service.post;

import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;

import java.util.Set;

public interface SavedPostService {
    Post savedPost(User currentUser, Post postToSaved);
    void unSavedPost(User currentUser, Post postToUnSave);

    Set<Post> getAllSavedPosts(User currentUser);
}

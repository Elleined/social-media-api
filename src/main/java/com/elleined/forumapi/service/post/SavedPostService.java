package com.elleined.forumapi.service.post;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

import java.util.List;
import java.util.Set;

public interface SavedPostService {
    Post savedPost(User currentUser, Post postToSaved);
    void unSavedPost(User currentUser, Post postToUnSave);

    Set<Post> getAllSavedPosts(User currentUser);
}

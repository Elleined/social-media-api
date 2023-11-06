package com.elleined.forumapi.service.post;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

import java.util.List;

public interface SavedPostService {
    Post savedPost(User currentUser, Post postToSaved);
    Post unSavedPost(User currentUser, Post postToUnSave);

    List<Post> getAllSavedPosts(User currentUser);
}

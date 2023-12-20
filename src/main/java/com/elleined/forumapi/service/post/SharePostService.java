package com.elleined.forumapi.service.post;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

import java.util.Collection;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
    void unSharePost(User currentUser, Post postToUnShare);

    Collection<Post> getAllSharedPosts(User currentUser);

}

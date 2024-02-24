package com.elleined.socialmediaapi.service.post;

import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;

import java.util.Collection;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
    void unSharePost(User currentUser, Post postToUnShare);

    Collection<Post> getAllSharedPosts(User currentUser);

}

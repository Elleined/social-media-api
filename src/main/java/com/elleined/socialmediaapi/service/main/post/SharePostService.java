package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;

import java.util.Collection;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
    void unSharePost(User currentUser, Post postToUnShare);

    Collection<Post> getAllSharedPosts(User currentUser);

}

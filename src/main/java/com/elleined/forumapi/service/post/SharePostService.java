package com.elleined.forumapi.service.post;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

public interface SharePostService {
    Post sharePost(User currentUser, Post postToShare);
}

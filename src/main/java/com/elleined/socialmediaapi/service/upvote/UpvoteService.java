package com.elleined.socialmediaapi.service.upvote;

import com.elleined.socialmediaapi.model.user.User;

public interface UpvoteService<T> {
    T upvote(User respondent, T t);
}

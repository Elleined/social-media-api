package com.elleined.socialmediaapi.service.upvote;

import com.elleined.socialmediaapi.model.User;

public interface UpvoteService<T> {
    T upvote(User respondent, T t);
}

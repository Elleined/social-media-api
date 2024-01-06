package com.elleined.forumapi.service.upvote;

import com.elleined.forumapi.model.User;

public interface UpvoteService<T> {
    T upvote(User respondent, T t);
}

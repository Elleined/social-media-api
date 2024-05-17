package com.elleined.socialmediaapi.service.main.comment.vote;

import com.elleined.socialmediaapi.model.user.User;

public interface VoteService<T> {
    T upVote(User respondent, T t);
    T downVote(User respondent, T t);
}

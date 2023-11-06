package com.elleined.forumapi.service;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;

public interface LikeService<T> {
    Like like(User respondent, T t);
    void unLike(User respondent, T t);
    boolean isLiked(User respondent, T t);
}

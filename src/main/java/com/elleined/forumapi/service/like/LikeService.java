package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;

import java.util.List;

public interface LikeService<T> {
    Like like(User respondent, T t);
    void unLike(User respondent, T t);
    boolean isLiked(User respondent, T t);
}

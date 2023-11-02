package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;

import java.util.List;

public interface LikeService<LIKE extends Like, T> {
    LIKE like(User respondent, T t);
    void unLike(User respondent, T t);
    boolean isLiked(User respondent, T t);
    List<LIKE> getAllUnreadNotification(User currentUser);
}

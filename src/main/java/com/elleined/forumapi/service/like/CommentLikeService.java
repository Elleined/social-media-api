package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.Like;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService implements LikeService<Comment> {
    @Override
    public CommentLike like(User respondent, Comment comment) {
        return null;
    }

    @Override
    public void unLike(User respondent, Comment comment) {

    }

    @Override
    public boolean isLiked(User respondent, Comment comment) {
        return false;
    }
}

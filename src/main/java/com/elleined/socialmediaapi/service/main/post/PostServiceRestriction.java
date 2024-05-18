package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;

public interface PostServiceRestriction {
    default boolean isCommentSectionClosed(Post post) {
        return post.getCommentSectionStatus() == Post.CommentSectionStatus.CLOSED;
    }

    default boolean isCommentSectionOpen(Post post) {
        return post.getCommentSectionStatus() == Post.CommentSectionStatus.OPEN;
    }

    default boolean hasPinnedComment(Post post) {
        return post.getPinnedComment() != null;
    }

    default boolean doesNotHavePinnedComment(Post post) {
        return post.getPinnedComment() == null;
    }

    default boolean owned(Post post, Comment comment) {
        return post.getComments().stream().anyMatch(comment::equals);
    }

    default boolean notOwned(Post post, Comment comment) {
        return post.getComments().stream().noneMatch(comment::equals);
    }
}

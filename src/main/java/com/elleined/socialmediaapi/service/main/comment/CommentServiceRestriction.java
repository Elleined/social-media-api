package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.model.user.User;

public interface CommentServiceRestriction {
    default boolean has(User currentUser, Reply reply) {
        return currentUser.getReplies().stream().anyMatch(reply::equals);
    }

    default boolean hasNot(User currentUser, Reply reply) {
        return !this.has(currentUser, reply);
    }

    default boolean isAlreadyVoted(User currentUser, Comment comment) {
        return currentUser.getVotedComments().stream()
                .map(Vote::getComment)
                .anyMatch(comment::equals);
    }
}

package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;

public interface CommentServiceRestriction {
    default boolean owned(Comment comment, Reply reply) {
        return comment.getReplies().stream().anyMatch(reply::equals);
    }

    default boolean notOwned(Comment comment, Reply reply) {
        return !this.owned(comment, reply);
    }

    default boolean hasPinnedReply(Comment comment ) {
        return comment.getPinnedReply() != null;
    }

    default boolean doesNotHavePinnedReply(Comment comment ) {
        return comment.getPinnedReply() == null;
    }

    default boolean isAlreadyVoted(User currentUser, Comment comment) {
        return currentUser.getVotedComments().stream()
                .map(Vote::getComment)
                .anyMatch(comment::equals);
    }

    default boolean notOwned(Comment comment, Reaction reaction) {
        return !comment.getReactions().contains(reaction);
    }
}

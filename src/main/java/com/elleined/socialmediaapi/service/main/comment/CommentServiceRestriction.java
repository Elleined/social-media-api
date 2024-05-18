package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;

public interface CommentServiceRestriction {
    default boolean has(User currentUser, Reply reply) {
        return currentUser.getReplies().stream().anyMatch(reply::equals);
    }

    default boolean hasNot(User currentUser, Reply reply) {
        return !this.has(currentUser, reply);
    }
}

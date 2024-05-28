package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;

public interface UserServiceRestriction {

    default boolean notOwned(User currentUser, Post post) {
        return currentUser.getPosts().stream().noneMatch(post::equals);
    }

    default boolean notOwned(User currentUser, Comment comment) {
        return currentUser.getComments().stream().noneMatch(comment::equals);
    }

    default boolean notOwned(User currentUser, Reply reply) {
        return currentUser.getReplies().stream().noneMatch(reply::equals);
    }

    default boolean notOwned(User currentUser, Reaction reaction) {
        return currentUser.getReactions().stream().noneMatch(reaction::equals);
    }

    default boolean notOwned(User currentUser, Story story) {
        return currentUser.getStory() != story;
    }
}

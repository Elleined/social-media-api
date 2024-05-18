package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.model.react.Reaction;
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

    // Comment
    default boolean isAlreadyVoted(User currentUser, Comment comment) {
        return currentUser.getVotedComments().stream()
                .map(Vote::getComment)
                .anyMatch(comment::equals);
    }

    // Story
    default boolean hasStory(User currentUser) {
        return currentUser.getStory() != null;
    }

    default boolean doesNotHaveStory(User currentUser) {
        return currentUser.getStory() == null;
    }

    // Note
    default boolean hasNote(User currentUser) {
        return currentUser.getNote() != null;
    }

    default boolean doesNotHaveNote(User currentUser) {
        return currentUser.getNote() == null;
    }

    // Friend
    default boolean isFriendsWith(User currentUser, User anotherUser) {
        return currentUser.getFriends().contains(anotherUser);
    }

    default boolean hasSendFriendRequestTo(User currentUser, User userToAdd) {
        return currentUser.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(userToAdd::equals);
    }

    default boolean hasReceiveFriendRequestTo(User currentUser, User userToAdd) {
        return userToAdd.getSentFriendRequests().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(currentUser::equals);
    }
}

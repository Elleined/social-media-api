package com.elleined.socialmediaapi.model;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    void postNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .posts(new ArrayList<>())
                .build();
        Post post = new Post();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(post));

        // Behavior Verifications
    }

    @Test
    void commentNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .comments(new ArrayList<>())
                .build();
        Comment comment = new Comment();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(comment));

        // Behavior Verifications
    }

    @Test
    void replyNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .replies(new ArrayList<>())
                .build();
        Reply reply = new Reply();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(reply));

        // Behavior Verifications
    }

    @Test
    void postReactNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdPostReactions(new HashSet<>())
                .build();
        PostReact postReact = new PostReact();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(postReact));

        // Behavior Verifications
    }

    @Test
    void commentReactNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdCommentReactions(new HashSet<>())
                .build();

        CommentReact commentReact = new CommentReact();
        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(commentReact));

        // Behavior Verifications
    }

    @Test
    void replyReactNotOwned() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdReplyReactions(new HashSet<>())
                .build();
        ReplyReact replyReact = new ReplyReact();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.notOwned(replyReact));

        // Behavior Verifications
    }

    @Test
    void isAlreadyReactedToPost() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdPostReactions(new HashSet<>())
                .build();

        Post post = new Post();

        PostReact postReact = PostReact.postReactBuilder()
                .post(post)
                .build();

        user.getCreatedPostReactions().add(postReact);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.isAlreadyReactedTo(post));

        // Behavior Verifications
    }

    @Test
    void isAlreadyReactedToComment() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdCommentReactions(new HashSet<>())
                .build();

        Comment comment = new Comment();

        CommentReact commentReact = CommentReact.commentReactBuilder()
                .comment(comment)
                .build();

        user.getCreatedCommentReactions().add(commentReact);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.isAlreadyReactedTo(comment));

        // Behavior Verifications
    }

    @Test
    void isAlreadyReactedToReply() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .createdReplyReactions(new HashSet<>())
                .build();

        Reply reply = new Reply();

        ReplyReact replyReact = ReplyReact.replyReactBuilder()
                .reply(reply)
                .build();

        user.getCreatedReplyReactions().add(replyReact);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.isAlreadyReactedTo(reply));

        // Behavior Verifications
    }

    @Test
    void isAlreadyUpvoted() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .upvotedComments(new HashSet<>())
                .build();

        Comment comment = new Comment();

        user.getUpvotedComments().add(comment);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.isAlreadyUpvoted(comment));

        // Behavior Verifications
    }

    @Test
    void isFriendsWith() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .friends(new HashSet<>())
                .build();

        User anotherUser = new User();

        user.getFriends().add(anotherUser);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.isFriendsWith(anotherUser));

        // Behavior Verifications
    }

    @Test
    void hasAlreadySentFriendRequestTo() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .sentFriendRequest(new HashSet<>())
                .build();

        User userToAdd = new User();

        FriendRequest friendRequest = FriendRequest.builder()
                .requestedUser(userToAdd)
                .build();

        user.getSentFriendRequest().add(friendRequest);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.hasSendFriendRequestTo(userToAdd));

        // Behavior Verifications
    }

    @Test
    void hasAlreadyReceiveFriendRequestTo() {
        // Expected and Actual Value

        // Mock Data
        User user = new User();

        User userToAdd = User.builder()
                .sentFriendRequest(new HashSet<>())
                .build();

        FriendRequest friendRequest = FriendRequest.builder()
                .requestedUser(user)
                .build();

        userToAdd.getSentFriendRequest().add(friendRequest);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.hasReceiveFriendRequestTo(userToAdd));

        // Behavior Verifications
    }

    @Test
    void hasNote() {
        // Expected and Actual Value

        // Mock Data
        User user = User.builder()
                .note(new Note())
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(user.hasNote());

        // Behavior Verifications
    }

    @Test
    void hasFriendRequest() {
        // Expected Value

        // Mock data
        FriendRequest friendRequest = new FriendRequest();
        User currentUser = User.builder()
                .receiveFriendRequest(new HashSet<>())
                .build();

        currentUser.getReceiveFriendRequest().add(friendRequest);

        // Set up method

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(currentUser.getReceiveFriendRequest().contains(friendRequest));

        // Behavior Verifications
    }
}
package com.elleined.forumapi.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class CommentTest {

    @Test
    void isCommentSectionClosed() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .post(Post.builder()
                        .commentSectionStatus(Post.CommentSectionStatus.CLOSED)
                        .build())
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(comment.getPost());
        assertNotNull(comment.getPost().getCommentSectionStatus());

        assertTrue(comment.isCommentSectionClosed());

        // Behavior Verifications
    }

    @Test
    void doesNotHave() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .replies(new ArrayList<>())
                .build();

        Reply reply = new Reply();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(comment.doesNotHave(reply));

        // Behavior Verifications
    }

    @Test
    void isInactive() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .status(Status.INACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(comment.isInactive());

        // Behavior Verifications
    }

    @Test
    void isActive() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .status(Status.ACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(comment.isActive());

        // Behavior Verifications
    }

    @Test
    void getUpvoteCount() {
        // Expected and Actual Value
        int expectedUpvoteCount = 2;

        // Mock Data
        Comment comment = Comment.builder()
                .upvotingUsers(new HashSet<>())
                .build();

        comment.getUpvotingUsers().add(new User());
        comment.getUpvotingUsers().add(new User());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertEquals(expectedUpvoteCount, comment.getUpvoteCount());

        // Behavior Verifications
    }

    @Test
    void isRead() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .notificationStatus(NotificationStatus.READ)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(comment.isRead());

        // Behavior Verifications
    }

    @Test
    void isUnread() {
        // Expected and Actual Value

        // Mock Data
        Comment comment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(comment.isUnread());

        // Behavior Verifications
    }
}
package com.elleined.socialmediaapi.model;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PostTest {

    @Test
    void isActive() {
        // Expected and Actual Value

        // Mock Data
        Post post = Post.builder()
                .status(Status.ACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(post.isActive());

        // Behavior Verifications
    }

    @Test
    void isInactive() {
        // Expected and Actual Value

        // Mock Data
        Post post = Post.builder()
                .status(Status.INACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(post.isInactive());

        // Behavior Verifications
    }

    @Test
    void isCommentSectionClosed() {
        // Expected and Actual Value

        // Mock Data
        Post post = Post.builder()
                .commentSectionStatus(Post.CommentSectionStatus.CLOSED)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(post.isCommentSectionClosed());

        // Behavior Verifications
    }

    @Test
    void isCommentSectionOpen() {
        // Expected and Actual Value

        // Mock Data
        Post post = Post.builder()
                .commentSectionStatus(Post.CommentSectionStatus.OPEN)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(post.isCommentSectionOpen());

        // Behavior Verifications
    }

    @Test
    void doesNotHave() {
        // Expected and Actual Value

        // Mock Data
        Post post = Post.builder()
                .comments(new ArrayList<>())
                .build();

        Comment comment = new Comment();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(post.doesNotHave(comment));

        // Behavior Verifications
    }
}
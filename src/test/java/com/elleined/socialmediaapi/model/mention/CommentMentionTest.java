package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class CommentMentionTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        CommentMention commentMention = spy(CommentMention.class);
        commentMention.setComment(Comment.builder()
                .body("Comment body")
                .build());

        commentMention.setMentioningUser(User.builder()
                .name("Mentioning user")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(commentMention.getComment());
        assertNotNull(commentMention.getComment().getBody());

        assertNotNull(commentMention.getMentioningUser());
        assertNotNull(commentMention.getMentioningUser().getName());

        assertNotNull(commentMention.getMessage(), "Comment mention message cannot be null!");
        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        CommentMention commentMention = spy(CommentMention.class);
        commentMention.setMentionedUser(User.builder()
                .id(expected)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(commentMention.getMentionedUser());

        assertEquals(expected, commentMention.getReceiverId());

        // Behavior Verifications
    }

    @Test
    void isEntityActive() {
        // Expected and Actual Value

        // Mock Data
        CommentMention commentMention = spy(CommentMention.class);
        commentMention.setComment(Comment.builder()
                .status(Status.ACTIVE)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(commentMention.getComment());
        assertNotNull(commentMention.getComment().getStatus());

        assertTrue(commentMention.isEntityActive());

        // Behavior Verifications
    }
}
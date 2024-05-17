package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class ReplyMentionTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        ReplyMention replyMention = spy(ReplyMention.class);
        replyMention.setReply(Reply.builder()
                .body("Reply body")
                .build());

        replyMention.setMentioningUser(User.builder()
                .name("Mentioning user")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(replyMention.getReply());
        assertNotNull(replyMention.getReply().getBody());

        assertNotNull(replyMention.getMentioningUser());
        assertNotNull(replyMention.getMentioningUser().getName());

        assertNotNull(replyMention.getMessage(), "Reply mention message cannot be null!");
        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        ReplyMention replyMention = spy(ReplyMention.class);
        replyMention.setMentionedUser(User.builder()
                .id(expected)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(replyMention.getMentionedUser());

        assertEquals(expected, replyMention.getReceiverId());

        // Behavior Verifications
    }

    @Test
    void isEntityActive() {
        // Expected and Actual Value

        // Mock Data
        ReplyMention replyMention = spy(ReplyMention.class);
        replyMention.setReply(Reply.builder()
                .status(Status.ACTIVE)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(replyMention.getReply());
        assertNotNull(replyMention.getReply().getStatus());

        assertTrue(replyMention.isEntityActive());

        // Behavior Verifications
    }
}
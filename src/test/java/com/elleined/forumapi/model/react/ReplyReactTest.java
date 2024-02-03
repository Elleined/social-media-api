package com.elleined.forumapi.model.react;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReplyReactTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        ReplyReact replyReact = spy(ReplyReact.class);
        replyReact.setReply(Reply.builder()
                .body("Reply body")
                .build());
        replyReact.setEmoji(Emoji.builder()
                .type(Emoji.Type.HAHA)
                .build());
        replyReact.setRespondent(User.builder()
                .name("Respondent name")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(replyReact.getReply());
        assertNotNull(replyReact.getReply().getBody());

        assertNotNull(replyReact.getEmoji());
        assertNotNull(replyReact.getEmoji().getType());

        assertNotNull(replyReact.getRespondent());
        assertNotNull(replyReact.getRespondent().getName());

        assertNotNull(replyReact.getMessage(), "Message cannot be null!");

        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        ReplyReact replyReact = spy(ReplyReact.class);
        replyReact.setReply(Reply.builder()
                .replier(User.builder()
                        .id(expected)
                        .build())
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(replyReact.getReply());
        assertNotNull(replyReact.getReply().getReplier());

        assertEquals(expected, replyReact.getReply().getReplier().getId());
        // Behavior Verifications
    }
}
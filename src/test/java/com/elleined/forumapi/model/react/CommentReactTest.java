package com.elleined.forumapi.model.react;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentReactTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        CommentReact commentReact = spy(CommentReact.class);
        commentReact.setComment(Comment.builder()
                .body("Comment body")
                .build());
        commentReact.setEmoji(Emoji.builder()
                .type(Emoji.Type.HAHA)
                .build());
        commentReact.setRespondent(User.builder()
                .name("Respondent name")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(commentReact.getComment());
        assertNotNull(commentReact.getComment().getBody());

        assertNotNull(commentReact.getEmoji());
        assertNotNull(commentReact.getEmoji().getType());

        assertNotNull(commentReact.getRespondent());
        assertNotNull(commentReact.getRespondent().getName());

        assertNotNull(commentReact.getMessage(), "Message cannot be null!");

        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        CommentReact commentReact = spy(CommentReact.class);
        commentReact.setComment(Comment.builder()
                .commenter(User.builder()
                        .id(expected)
                        .build())
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(commentReact.getComment());
        assertNotNull(commentReact.getComment().getCommenter());

        assertEquals(expected, commentReact.getComment().getCommenter().getId());
        // Behavior Verifications
    }
}
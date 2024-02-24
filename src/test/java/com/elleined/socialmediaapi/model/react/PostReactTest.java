package com.elleined.socialmediaapi.model.react;

import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class PostReactTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        PostReact postReact = spy(PostReact.class);
        postReact.setPost(Post.builder()
                .body("Post body")
                .build());
        postReact.setEmoji(Emoji.builder()
                .type(Emoji.Type.HAHA)
                .build());
        postReact.setRespondent(User.builder()
                .name("Respondent name")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(postReact.getPost());
        assertNotNull(postReact.getPost().getBody());

        assertNotNull(postReact.getEmoji());
        assertNotNull(postReact.getEmoji().getType());

        assertNotNull(postReact.getRespondent());
        assertNotNull(postReact.getRespondent().getName());

        assertNotNull(postReact.getMessage(), "Message cannot be null!");

        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        PostReact postReact = spy(PostReact.class);
        postReact.setPost(Post.builder()
                .author(User.builder()
                        .id(expected)
                        .build())
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(postReact.getPost());
        assertNotNull(postReact.getPost().getAuthor());

        assertEquals(expected, postReact.getPost().getAuthor().getId());
        // Behavior Verifications
    }
}
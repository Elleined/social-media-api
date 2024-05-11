package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class PostMentionTest {

    @Test
    void getMessage() {
        // Expected and Actual Value

        // Mock Data
        PostMention postMention = spy(PostMention.class);
        postMention.setPost(Post.builder()
                .body("Post body")
                .build());

        postMention.setMentioningUser(User.builder()
                .name("Mentioning user")
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(postMention.getPost());
        assertNotNull(postMention.getPost().getBody());

        assertNotNull(postMention.getMentioningUser());
        assertNotNull(postMention.getMentioningUser().getName());

        assertNotNull(postMention.getMessage(), "Post mention message cannot be null!");
        // Behavior Verifications
    }

    @Test
    void getReceiverId() {
        // Expected and Actual Value
        int expected = 1;

        // Mock Data
        PostMention postMention = spy(PostMention.class);
        postMention.setMentionedUser(User.builder()
                .id(expected)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(postMention.getMentionedUser());

        assertEquals(expected, postMention.getReceiverId());

        // Behavior Verifications
    }

    @Test
    void isEntityActive() {
        // Expected and Actual Value

        // Mock Data
        PostMention postMention = spy(PostMention.class);
        postMention.setPost(Post.builder()
                .status(Status.ACTIVE)
                .build());

        // Stubbing methods

        // Calling the method
        // Assertions
        assertNotNull(postMention.getPost());
        assertNotNull(postMention.getPost().getStatus());

        assertTrue(postMention.isEntityActive());

        // Behavior Verifications
    }
}
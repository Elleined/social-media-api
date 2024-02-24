
package com.elleined.forumapi.service.pin;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostPinCommentServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostPinCommentService postPinCommentService;


    @Test
    void pin() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Post post = spy(Post.class);
        post.setAuthor(currentUser);

        Comment reply = spy(Comment.class);

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Post.class));
        doReturn(false).when(post).doesNotHave(any(Comment.class));
        doReturn(false).when(reply).isInactive();
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Calling the method
        postPinCommentService.pin(currentUser, post, reply);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));

        // Assertions
        assertNotNull(post.getPinnedComment());
        assertEquals(reply, post.getPinnedComment());
    }

    @Test
    void unpin() {
        // Expected Value

        // Mock data
        Comment reply = new Comment();

        Post post = new Post();
        post.setPinnedComment(reply);

        // Set up method

        // Stubbing methods
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Calling the method
        postPinCommentService.unpin(post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));

        // Assertions
        assertNull(post.getPinnedComment());
    }

    @Test
    void getPinned() {
        // Expected Value

        // Mock data
        Comment expectedComment = new Comment();

        Post post = spy(Post.class);
        post.setPinnedComment(expectedComment);

        // Set up method

        // Stubbing methods
        doReturn(false).when(post).isInactive();

        // Calling the method
        postPinCommentService.getPinned(post);

        // Behavior Verifications

        // Assertions
        assertNotNull(post.getPinnedComment());
        assertEquals(expectedComment, post.getPinnedComment());
    }
}
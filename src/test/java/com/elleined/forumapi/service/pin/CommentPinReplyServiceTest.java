package com.elleined.forumapi.service.pin;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentPinReplyServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private CommentPinReplyService commentPinReplyService;

    @Test
    void pin() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Comment comment = spy(Comment.class);
        comment.setCommenter(currentUser);

        Reply reply = spy(Reply.class);

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Comment.class));
        doReturn(false).when(comment).doesNotHave(any(Reply.class));
        doReturn(false).when(reply).isInactive();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Calling the method
        commentPinReplyService.pin(currentUser, comment, reply);

        // Behavior Verifications
        verify(commentRepository).save(any(Comment.class));

        // Assertions
        assertNotNull(comment.getPinnedReply());
        assertEquals(reply, comment.getPinnedReply());
    }

    @Test
    void unpin() {
        // Expected Value

        // Mock data
        Reply reply = new Reply();

        Comment comment = new Comment();
        comment.setPinnedReply(reply);

        // Set up method

        // Stubbing methods
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Calling the method
        commentPinReplyService.unpin(comment);

        // Behavior Verifications
        verify(commentRepository).save(any(Comment.class));

        // Assertions
        assertNull(comment.getPinnedReply());
    }

    @Test
    void getPinned() {
        // Expected Value

        // Mock data
        Reply expectedReply = new Reply();

        Comment comment = spy(Comment.class);
        comment.setPinnedReply(expectedReply);

        // Set up method

        // Stubbing methods
        doReturn(false).when(comment).isInactive();

        // Calling the method
        commentPinReplyService.getPinned(comment);

        // Behavior Verifications

        // Assertions
        assertNotNull(comment.getPinnedReply());
        assertEquals(expectedReply, comment.getPinnedReply());
    }
}
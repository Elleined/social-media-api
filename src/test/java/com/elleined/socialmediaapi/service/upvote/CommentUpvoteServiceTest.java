package com.elleined.socialmediaapi.service.upvote;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class CommentUpvoteServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentUpvoteService commentUpvoteService;

    @Test
    void upvote() {
        // Expected Value

        // Mock data
        User respondent = spy(User.class);
        respondent.setUpvotedComments(new HashSet<>());

        Comment comment = spy(Comment.class);
        comment.setUpvotingUsers(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(userRepository.save(any(User.class))).thenReturn(respondent);

        // Calling the method
        commentUpvoteService.upvote(respondent, comment);

        // Behavior Verifications
        verify(commentRepository).save(any(Comment.class));
        verify(userRepository).save(any(User.class));

        // Assertions
        assertTrue(comment.getUpvotingUsers().contains(respondent));
        assertTrue(respondent.getUpvotedComments().contains(comment));
    }
}
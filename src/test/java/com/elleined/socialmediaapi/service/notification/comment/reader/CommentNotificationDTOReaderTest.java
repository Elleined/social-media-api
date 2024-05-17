package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.service.notification.comment.CommentNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentNotificationDTOReaderTest {

    @Mock
    private CommentNotificationService commentNotificationService;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentNotificationReader commentNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Post post = new Post();

        Comment unreadComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .post(post)
                .build();

        Comment unreadCommentInAnotherPost = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .post(new Post())
                .build();

        // Set up method
        List<Comment> commentMentions = List.of(unreadComment, unreadCommentInAnotherPost);

        // Stubbing methods
        when(commentNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(commentMentions);
        when(commentRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        commentNotificationReader.readAll(new User(), post);

        // Behavior Verifications
        verify(commentNotificationService).getAllUnreadNotification(any(User.class));
        verify(commentRepository).saveAll(anyList());

        // Assertions
        assertTrue(unreadComment.isRead());
        assertTrue(unreadCommentInAnotherPost.isUnread());
    }
}
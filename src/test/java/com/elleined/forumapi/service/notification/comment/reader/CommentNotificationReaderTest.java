package com.elleined.forumapi.service.notification.comment.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.service.notification.comment.CommentNotificationService;
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
class CommentNotificationReaderTest {

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
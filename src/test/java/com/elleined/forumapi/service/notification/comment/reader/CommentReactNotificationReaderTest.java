package com.elleined.forumapi.service.notification.comment.reader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.repository.react.CommentReactRepository;
import com.elleined.forumapi.service.notification.react.CommentReactNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CommentReactNotificationReaderTest {

    @Mock
    private CommentReactNotificationService commentReactNotificationService;
    @Mock
    private CommentReactRepository commentReactRepository;

    @InjectMocks
    private CommentReactNotificationReader commentReactNotificationReader;


    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Post post = new Post();

        CommentReact unreadCommentReact = CommentReact.commentReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .comment(Comment.builder()
                        .post(post)
                        .build())
                .build();

        CommentReact unreadCommentReactInAnotherPost = CommentReact.commentReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .comment(Comment.builder()
                        .post(new Post())
                        .build())
                .build();

        // Set up method
        List<CommentReact> commentReacts = List.of(unreadCommentReact, unreadCommentReactInAnotherPost);

        // Stubbing methods
        when(commentReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(commentReacts);
        when(commentReactRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        commentReactNotificationReader.readAll(new User(), post);

        // Behavior Verifications
        verify(commentReactNotificationService).getAllUnreadNotification(any(User.class));
        verify(commentReactRepository).saveAll(anyList());

        // Assertions
        assertTrue(unreadCommentReact.isRead());
        assertTrue(unreadCommentReactInAnotherPost.isUnread());
    }
}
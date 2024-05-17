package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.react.CommentReactRepository;
import com.elleined.socialmediaapi.service.notification.react.CommentReactNotificationService;
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
class CommentReactNotificationDTOReaderTest {

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
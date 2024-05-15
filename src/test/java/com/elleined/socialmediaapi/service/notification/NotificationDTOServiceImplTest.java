package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.dto.notification.Notification;
import com.elleined.socialmediaapi.mapper.notification.comment.CommentNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.friend.FriendRequestNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.react.ReactNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.reply.ReplyNotificationMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.comment.CommentNotificationService;
import com.elleined.socialmediaapi.service.notification.friend.FriendRequestNotificationService;
import com.elleined.socialmediaapi.service.notification.mention.CommentMentionNotificationService;
import com.elleined.socialmediaapi.service.notification.mention.PostMentionNotificationService;
import com.elleined.socialmediaapi.service.notification.mention.ReplyMentionNotificationService;
import com.elleined.socialmediaapi.service.notification.react.CommentReactNotificationService;
import com.elleined.socialmediaapi.service.notification.react.PostReactNotificationService;
import com.elleined.socialmediaapi.service.notification.react.ReplyReactNotificationService;
import com.elleined.socialmediaapi.service.notification.reply.ReplyNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationDTOServiceImplTest {
    // Notification Services

    // Mentions
    @Mock
    private PostMentionNotificationService postMentionNotificationService;
    @Mock
    private CommentMentionNotificationService commentMentionNotificationService;
    @Mock
    private ReplyMentionNotificationService replyMentionNotificationService;

    // Reactions
    @Mock
    private PostReactNotificationService postReactNotificationService;
    @Mock
    private CommentReactNotificationService commentReactNotificationService;
    @Mock
    private ReplyReactNotificationService replyReactNotificationService;

    // Entities
    @Mock
    private CommentNotificationService commentNotificationService;
    @Mock
    private ReplyNotificationService replyNotificationService;
    @Mock
    private FriendRequestNotificationService friendRequestNotificationService;

    // Mappers
    @Mock
    private CommentNotificationMapper commentNotificationMapper;
    @Mock
    private ReplyNotificationMapper replyNotificationMapper;
    @Mock
    private MentionNotificationMapper mentionNotificationMapper;
    @Mock
    private FriendRequestNotificationMapper friendRequestNotificationMapper;
    @Mock
    private ReactNotificationMapper reactNotificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Services
        when(postMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(commentMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(replyMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(postReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(commentReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(replyReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(commentNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(replyNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());
        when(friendRequestNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>());

        // Calling the method
        Set<Notification> actual = notificationService.getAllUnreadNotification(new User());

        // Behavior Verifications
        verify(postMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(commentMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(replyMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(postReactNotificationService).getAllUnreadNotification(any(User.class));
        verify(commentReactNotificationService).getAllUnreadNotification(any(User.class));
        verify(replyReactNotificationService).getAllUnreadNotification(any(User.class));
        verify(commentNotificationService).getAllUnreadNotification(any(User.class));
        verify(replyNotificationService).getAllUnreadNotification(any(User.class));
        verify(friendRequestNotificationService).getAllUnreadNotification(any(User.class));

        // Assertions
        assertTrue(actual.isEmpty());
    }
}
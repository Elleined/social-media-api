package com.elleined.socialmediaapi.service.notification.reply;

import com.elleined.socialmediaapi.model.*;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyNotificationServiceImplTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private ReplyNotificationServiceImpl replyNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Comment 1
        Reply comment1UnreadReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Reply comment1ActiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Reply comment1UnreadButInactiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.INACTIVE)
                .build();

        Reply comment1ReadButActiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.READ)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Comment comment1 = Comment.builder()
                .replies(List.of(comment1ActiveReply, comment1UnreadReply, comment1UnreadButInactiveReply, comment1ReadButActiveReply))
                .build();

        // Comment 2
        Reply comment2UnreadReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Reply comment2ActiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Reply comment2UnreadButInactiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .replier(new User())
                .status(Status.INACTIVE)
                .build();

        Reply comment2ReadButActiveReply = Reply.builder()
                .notificationStatus(NotificationStatus.READ)
                .replier(new User())
                .status(Status.ACTIVE)
                .build();

        Comment comment2 = Comment.builder()
                .replies(List.of(comment2ActiveReply, comment2UnreadReply, comment2UnreadButInactiveReply, comment2ReadButActiveReply))
                .build();

        // Set up method
        List<Comment> comments = List.of(comment1, comment2);
        currentUser.setComments(comments);

        List<Reply> expectedReplies = List.of(comment1ActiveReply, comment1UnreadReply, comment2ActiveReply, comment2UnreadReply);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<Reply> actualReplies = replyNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedReplies, actualReplies);
    }
}
package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyMentionNotificationServiceTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private ReplyMentionNotificationService replyMentionNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        currentUser.setReceiveReplyMentions(new HashSet<>());

        ReplyMention unreadButActiveReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .reply(Reply.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        ReplyMention unreadButInactiveReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .reply(Reply.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        ReplyMention readButInactiveReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .reply(Reply.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        ReplyMention readButActiveReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .reply(Reply.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        // Set up method
        currentUser.setReceiveReplyMentions(Set.of(unreadButActiveReply, unreadButInactiveReply,readButActiveReply, readButInactiveReply));

        List<ReplyMention> expectedMentions = List.of(unreadButActiveReply);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<ReplyMention> actualMentions = replyMentionNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedMentions, actualMentions);
    }
}
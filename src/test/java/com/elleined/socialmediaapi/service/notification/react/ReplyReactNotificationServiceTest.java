package com.elleined.socialmediaapi.service.notification.react;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyReactNotificationServiceTest {


    @Mock
    private BlockService blockService;

    @InjectMocks
    private ReplyReactNotificationService replyReactNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Active Reply
        ReplyReact unreadReact = ReplyReact.replyReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        ReplyReact readReact = ReplyReact.replyReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.READ)
                .build();

        Reply activeReply = Reply.builder()
                .reactions(List.of(unreadReact, readReact))
                .status(Status.ACTIVE)
                .build();

        // Inactive Reply
        Reply inactiveReply = Reply.builder()
                .reactions(new ArrayList<>())
                .status(Status.INACTIVE)
                .build();

        // Set up method
        currentUser.setReplies(List.of(activeReply, inactiveReply));

        List<ReplyReact> expectedReactions = List.of(unreadReact);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<ReplyReact> actualReactions = replyReactNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedReactions, actualReactions);
    }
}
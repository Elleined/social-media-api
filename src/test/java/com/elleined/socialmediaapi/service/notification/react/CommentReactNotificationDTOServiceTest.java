package com.elleined.socialmediaapi.service.notification.react;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
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
class CommentReactNotificationDTOServiceTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private CommentReactNotificationService commentReactNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Active Comment
        CommentReact unreadReact = CommentReact.commentReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        CommentReact readReact = CommentReact.commentReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.READ)
                .build();

        Comment activeComment = Comment.builder()
                .reactions(List.of(unreadReact, readReact))
                .status(Status.ACTIVE)
                .build();

        // Inactive Comment
        Comment inactiveComment = Comment.builder()
                .reactions(new ArrayList<>())
                .status(Status.INACTIVE)
                .build();

        // Set up method
        currentUser.setComments(List.of(activeComment, inactiveComment));

        List<CommentReact> expectedReactions = List.of(unreadReact);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<CommentReact> actualReactions = commentReactNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedReactions, actualReactions);
    }
}
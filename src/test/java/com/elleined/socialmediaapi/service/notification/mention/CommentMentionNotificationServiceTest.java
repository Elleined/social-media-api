package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
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
class CommentMentionNotificationServiceTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private CommentMentionNotificationService commentMentionNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        currentUser.setReceiveCommentMentions(new HashSet<>());

        CommentMention unreadButActiveComment = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .comment(Comment.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        CommentMention unreadButInactiveComment = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .comment(Comment.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        CommentMention readButInactiveComment = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .comment(Comment.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        CommentMention readButActiveComment = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .comment(Comment.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        // Set up method
        currentUser.setReceiveCommentMentions(Set.of(unreadButActiveComment, unreadButInactiveComment,readButActiveComment, readButInactiveComment));

        List<CommentMention> expectedMentions = List.of(unreadButActiveComment);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<CommentMention> actualMentions = commentMentionNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedMentions, actualMentions);
    }

}
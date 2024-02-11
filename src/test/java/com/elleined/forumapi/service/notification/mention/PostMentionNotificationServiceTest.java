package com.elleined.forumapi.service.notification.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.service.block.BlockService;
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
class PostMentionNotificationServiceTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private PostMentionNotificationService postMentionNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        currentUser.setReceivePostMentions(new HashSet<>());

        PostMention unreadButActivePost = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .post(Post.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        PostMention unreadButInactivePost = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .mentionedUser(new User())
                .post(Post.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        PostMention readButInactivePost = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .post(Post.builder()
                        .status(Status.INACTIVE)
                        .build())
                .build();

        PostMention readButActivePost = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.READ)
                .mentionedUser(new User())
                .post(Post.builder()
                        .status(Status.ACTIVE)
                        .build())
                .build();

        // Set up method
        currentUser.setReceivePostMentions(Set.of(unreadButActivePost, unreadButInactivePost,readButActivePost, readButInactivePost));

        List<PostMention> expectedMentions = List.of(unreadButActivePost);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<PostMention> actualMentions = postMentionNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedMentions, actualMentions);
    }
}
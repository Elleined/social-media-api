package com.elleined.forumapi.service.notification.react;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.service.block.BlockService;
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
class PostReactNotificationServiceTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private PostReactNotificationService postReactNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Active Post
        PostReact unreadReact = PostReact.postReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        PostReact readReact = PostReact.postReactBuilder()
                .respondent(new User())
                .notificationStatus(NotificationStatus.READ)
                .build();

        Post activePost = Post.builder()
                .reactions(List.of(unreadReact, readReact))
                .status(Status.ACTIVE)
                .build();

        // Inactive Post
        Post inactivePost = Post.builder()
                .reactions(new ArrayList<>())
                .status(Status.INACTIVE)
                .build();

        // Set up method
        currentUser.setPosts(List.of(activePost, inactivePost));

        List<PostReact> expectedReactions = List.of(unreadReact);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<PostReact> actualReactions = postReactNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedReactions, actualReactions);
    }
}
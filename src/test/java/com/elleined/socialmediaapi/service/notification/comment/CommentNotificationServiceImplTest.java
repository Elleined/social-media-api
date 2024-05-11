package com.elleined.socialmediaapi.service.notification.comment;

import com.elleined.socialmediaapi.model.*;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
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
class CommentNotificationServiceImplTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private CommentNotificationServiceImpl commentNotificationService;

    @Test
    void getAllUnreadNotification() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        // Post 1
        Comment post1UnreadComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Comment post1ActiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Comment post1UnreadButInactiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.INACTIVE)
                .build();

        Comment post1ReadButActiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.READ)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Post post1 = Post.builder()
                .comments(List.of(post1ActiveComment, post1UnreadComment, post1UnreadButInactiveComment, post1ReadButActiveComment))
                .build();

        // Post 2
        Comment post2UnreadComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Comment post2ActiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Comment post2UnreadButInactiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .commenter(new User())
                .status(Status.INACTIVE)
                .build();

        Comment post2ReadButActiveComment = Comment.builder()
                .notificationStatus(NotificationStatus.READ)
                .commenter(new User())
                .status(Status.ACTIVE)
                .build();

        Post post2 = Post.builder()
                .comments(List.of(post2ActiveComment, post2UnreadComment, post2UnreadButInactiveComment, post2ReadButActiveComment))
                .build();

        // Set up method
        List<Post> posts = List.of(post1, post2);
        currentUser.setPosts(posts);

        List<Comment> expectedComments = List.of(post1ActiveComment, post1UnreadComment, post2ActiveComment, post2UnreadComment);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<Comment> actualComments = commentNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedComments, actualComments);
    }
}
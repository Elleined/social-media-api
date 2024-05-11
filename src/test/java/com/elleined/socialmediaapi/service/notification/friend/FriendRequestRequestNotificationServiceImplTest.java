package com.elleined.socialmediaapi.service.notification.friend;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(MockitoExtension.class)
class FriendRequestRequestNotificationServiceImplTest {

    @Test
    void getAllUnreadNotification() {
        // Expected Value
        FriendRequestNotificationService friendRequestNotificationService = new FriendRequestRequestNotificationServiceImpl();

        // Mock data
        FriendRequest unread = FriendRequest.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        FriendRequest read = FriendRequest.builder()
                .notificationStatus(NotificationStatus.READ)
                .build();

        Set<FriendRequest> friendRequests = Set.of(unread, read);

        User currentUser = User.builder()
                .receiveFriendRequest(friendRequests)
                .build();

        // Set up method
        Set<FriendRequest> expected = Set.of(unread);

        // Stubbing methods

        // Calling the method
        List<FriendRequest> actual = friendRequestNotificationService.getAllUnreadNotification(currentUser);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expected, actual);
    }
}
package com.elleined.socialmediaapi.model.friend;

import com.elleined.socialmediaapi.model.NotificationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class FriendRequestTest {

    @Test
    void isRead() {
        // Expected and Actual Value

        // Mock Data
        FriendRequest friendRequest = spy(FriendRequest.class);
        friendRequest.setNotificationStatus(NotificationStatus.READ);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(friendRequest.isRead());

        // Behavior Verifications
    }

    @Test
    void isUnRead() {
        // Expected and Actual Value

        // Mock Data
        FriendRequest friendRequest = spy(FriendRequest.class);
        friendRequest.setNotificationStatus(NotificationStatus.UNREAD);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(friendRequest.isUnRead());

        // Behavior Verifications
    }
}
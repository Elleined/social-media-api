package com.elleined.forumapi.model.friend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.NotificationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
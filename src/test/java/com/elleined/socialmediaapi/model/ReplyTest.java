package com.elleined.socialmediaapi.model;

import com.elleined.socialmediaapi.model.main.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ReplyTest {

    @Test
    void isInactive() {
        // Expected and Actual Value

        // Mock Data
        Reply reply = Reply.builder()
                .status(Status.INACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(reply.isInactive());

        // Behavior Verifications
    }

    @Test
    void isActive() {
        // Expected and Actual Value

        // Mock Data
        Reply reply = Reply.builder()
                .status(Status.ACTIVE)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(reply.isActive());

        // Behavior Verifications
    }

    @Test
    void isRead() {
        // Expected and Actual Value

        // Mock Data
        Reply reply = Reply.builder()
                .notificationStatus(NotificationStatus.READ)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(reply.isRead());

        // Behavior Verifications
    }

    @Test
    void isUnread() {
        // Expected and Actual Value

        // Mock Data
        Reply reply = Reply.builder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(reply.isUnread());

        // Behavior Verifications
    }
}
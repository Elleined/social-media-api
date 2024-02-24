package com.elleined.socialmediaapi.model.react;

import com.elleined.socialmediaapi.model.NotificationStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class ReactTest {

    @ParameterizedTest
    @ValueSource(classes = {PostReact.class, CommentReact.class, ReplyReact.class})
    void isRead(Class<? extends React> reactClass) {
        // Expected and Actual Value

        // Mock Data
        React react = spy(reactClass);
        react.setNotificationStatus(NotificationStatus.READ);

        // Stubbing methods

        // Calling the method

        // Assertions
        assertNotNull(react.getNotificationStatus());
        assertTrue(react.isRead());

        // Behavior Verifications
    }

    @ParameterizedTest
    @ValueSource(classes = {PostReact.class, CommentReact.class, ReplyReact.class})
    void isUnread(Class<? extends React> reactClass) {
        // Expected and Actual Value

        // Mock Data
        React react = spy(reactClass);
        react.setNotificationStatus(NotificationStatus.UNREAD);

        // Stubbing methods

        // Calling the method

        // Assertions
        assertNotNull(react.getNotificationStatus());
        assertTrue(react.isUnread());

        // Behavior Verifications
    }
}
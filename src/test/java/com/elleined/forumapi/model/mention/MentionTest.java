package com.elleined.forumapi.model.mention;

import com.elleined.forumapi.model.NotificationStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class MentionTest {

    @ParameterizedTest
    @ValueSource(classes = {PostMention.class, CommentMention.class, ReplyMention.class})
    void isRead(Class<? extends Mention> mentionClass) {
        // Expected and Actual Value

        // Mock Data
        Mention mention = spy(mentionClass);
        mention.setNotificationStatus(NotificationStatus.READ);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(mention.isRead());

        // Behavior Verifications
    }

    @ParameterizedTest
    @ValueSource(classes = {PostMention.class, CommentMention.class, ReplyMention.class})
    void isUnread(Class<? extends Mention> mentionClass) {
        // Expected and Actual Value

        // Mock Data
        Mention mention = spy(mentionClass);
        mention.setNotificationStatus(NotificationStatus.UNREAD);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertTrue(mention.isUnread());

        // Behavior Verifications
    }
}
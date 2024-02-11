package com.elleined.forumapi.service.notification.post.reader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.notification.mention.PostMentionNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PostMentionNotificationReaderTest {

    @Mock
    private PostMentionNotificationService postMentionNotificationService;
    @Mock
    private MentionRepository mentionRepository;

    @InjectMocks
    private PostMentionNotificationReader postMentionNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        PostMention postMention1 = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        PostMention postMention2 = PostMention.postMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        List<PostMention> postMentions = List.of(postMention1, postMention2);

        // Set up method

        // Stubbing methods
        when(postMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(postMentions);
        when(mentionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        postMentionNotificationReader.readAll(new User());

        // Behavior Verifications
        verify(postMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(mentionRepository).saveAll(anyList());

        // Assertions
        assertTrue(postMention1.isRead());
        assertTrue(postMention2.isRead());
    }
}
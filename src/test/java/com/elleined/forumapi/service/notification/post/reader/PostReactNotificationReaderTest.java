package com.elleined.forumapi.service.notification.post.reader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.repository.react.PostReactRepository;
import com.elleined.forumapi.service.notification.react.PostReactNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PostReactNotificationReaderTest {

    @Mock
    private PostReactNotificationService postReactNotificationService;
    @Mock
    private PostReactRepository postReactRepository;

    @InjectMocks
    private PostReactNotificationReader postReactNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        PostReact postReact1 = PostReact.postReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        PostReact postReact2 = PostReact.postReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        // Set up method
        List<PostReact> postReacts = List.of(postReact1, postReact2);

        // Stubbing methods
        when(postReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(postReacts);

        // Calling the method
        postReactNotificationReader.readAll(new User());

        // Behavior Verifications
        verify(postReactNotificationService).getAllUnreadNotification(any(User.class));

        // Assertions
        assertTrue(postReact1.isRead());
        assertTrue(postReact2.isRead());
    }
}
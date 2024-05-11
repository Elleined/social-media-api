package com.elleined.socialmediaapi.service.notification.reply.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.react.ReplyReactRepository;
import com.elleined.socialmediaapi.service.notification.react.ReplyReactNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyReactNotificationReaderTest {


    @Mock
    private ReplyReactNotificationService replyReactNotificationService;
    @Mock
    private ReplyReactRepository replyReactRepository;

    @InjectMocks
    private ReplyReactNotificationReader replyReactNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Comment comment = new Comment();

        ReplyReact commentReply = ReplyReact.replyReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .reply(Reply.builder()
                        .comment(comment)
                        .build())
                .build();

        ReplyReact otherCommentReply = ReplyReact.replyReactBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .reply(Reply.builder()
                        .comment(new Comment())
                        .build())
                .build();

        // Set up method
        Set<ReplyReact> replyMentions = Set.of(commentReply, otherCommentReply);

        // Stubbing methods
        when(replyReactNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>(replyMentions));
        when(replyReactRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        replyReactNotificationReader.readAll(new User(), comment);

        // Behavior Verifications
        verify(replyReactNotificationService).getAllUnreadNotification(any(User.class));
        verify(replyReactRepository).saveAll(anyList());

        // Assertions
        assertTrue(commentReply.isRead());
        assertTrue(otherCommentReply.isUnread());
    }
}
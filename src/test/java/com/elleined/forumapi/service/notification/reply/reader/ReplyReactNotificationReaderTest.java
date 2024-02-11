package com.elleined.forumapi.service.notification.reply.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.repository.react.ReplyReactRepository;
import com.elleined.forumapi.service.notification.react.ReplyReactNotificationService;
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
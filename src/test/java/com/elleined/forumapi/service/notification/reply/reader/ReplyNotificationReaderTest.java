package com.elleined.forumapi.service.notification.reply.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.notification.reply.ReplyNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyNotificationReaderTest {

    @Mock
    private ReplyNotificationService replyNotificationService;
    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyNotificationReader replyNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Comment comment = new Comment();
        Reply commentReply = Reply.builder()
                .comment(comment)
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        Reply otherCommentReply = Reply.builder()
                .comment(new Comment())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        // Set up method

        // Stubbing methods
        when(replyNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(List.of(commentReply, otherCommentReply));
        when(replyRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        replyNotificationReader.readAll(new User(), comment);

        // Behavior Verifications
        verify(replyNotificationService).getAllUnreadNotification(any(User.class));
        verify(replyRepository).saveAll(anyList());

        // Assertions
        assertTrue(commentReply.isRead());
        assertTrue(otherCommentReply.isUnread());
    }
}
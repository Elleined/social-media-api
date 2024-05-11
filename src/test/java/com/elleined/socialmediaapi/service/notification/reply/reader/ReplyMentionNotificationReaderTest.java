package com.elleined.socialmediaapi.service.notification.reply.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.MentionRepository;
import com.elleined.socialmediaapi.service.notification.mention.ReplyMentionNotificationService;
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
class ReplyMentionNotificationReaderTest {

    @Mock
    private ReplyMentionNotificationService replyMentionNotificationService;
    @Mock
    private MentionRepository mentionRepository;

    @InjectMocks
    private ReplyMentionNotificationReader replyMentionNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Comment comment = new Comment();

        ReplyMention commentReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .reply(Reply.builder()
                        .comment(comment)
                        .build())
                .build();

        ReplyMention otherCommentReply = ReplyMention.replyMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .reply(Reply.builder()
                        .comment(new Comment())
                        .build())
                .build();

        // Set up method
        Set<ReplyMention> replyMentions = Set.of(commentReply, otherCommentReply);

        // Stubbing methods
        when(replyMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(new ArrayList<>(replyMentions));
        when(mentionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        replyMentionNotificationReader.readAll(new User(), comment);

        // Behavior Verifications
        verify(replyMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(mentionRepository).saveAll(anyList());

        // Assertions
        assertTrue(commentReply.isRead());
        assertTrue(otherCommentReply.isUnread());
    }
}
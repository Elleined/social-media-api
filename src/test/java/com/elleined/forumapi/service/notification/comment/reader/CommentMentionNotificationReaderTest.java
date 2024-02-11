package com.elleined.forumapi.service.notification.comment.reader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.notification.mention.CommentMentionNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CommentMentionNotificationReaderTest {

    @Mock
    private CommentMentionNotificationService commentMentionNotificationService;
    @Mock
    private MentionRepository mentionRepository;

    @InjectMocks
    private CommentMentionNotificationReader commentMentionNotificationReader;

    @Test
    void readAll() {
        // Expected Value

        // Mock data
        Post post = new Post();

        CommentMention unreadCommentMention = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .comment(Comment.builder()
                        .post(post)
                        .build())
                .build();

        CommentMention unreadCommentMentionInAnotherPost = CommentMention.commentMentionBuilder()
                .notificationStatus(NotificationStatus.UNREAD)
                .comment(Comment.builder()
                        .post(new Post())
                        .build())
                .build();

        // Set up method
        List<CommentMention> commentMentions = List.of(unreadCommentMention, unreadCommentMentionInAnotherPost);

        // Stubbing methods
        when(commentMentionNotificationService.getAllUnreadNotification(any(User.class))).thenReturn(commentMentions);
        when(mentionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Calling the method
        commentMentionNotificationReader.readAll(new User(), post);

        // Behavior Verifications
        verify(commentMentionNotificationService).getAllUnreadNotification(any(User.class));
        verify(mentionRepository).saveAll(anyList());

        // Assertions
        assertTrue(unreadCommentMention.isRead());
        assertTrue(unreadCommentMentionInAnotherPost.isUnread());
    }
}
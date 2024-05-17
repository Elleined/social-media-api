package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.service.notification.mention.CommentMentionNotificationService;
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
class CommentMentionNotificationDTOReaderTest {

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
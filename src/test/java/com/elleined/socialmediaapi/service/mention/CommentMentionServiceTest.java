package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.mapper.mention.CommentMentionMapper;
import com.elleined.socialmediaapi.model.*;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.service.ModalTrackerService;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentMentionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private MentionRepository mentionRepository;
    @Mock
    private CommentMentionMapper commentMentionMapper;

    @InjectMocks
    private CommentMentionService commentMentionService;

    @Test
    void mention() {
        // Expected Value

        // Mock data
        User mentioningUser = new User();
        User mentionedUser = new User();
        Comment comment = spy(Comment.class);
        comment.setPost(Post.builder()
                .id(1)
                .build());

        CommentMention expected = new CommentMention();

        // Set up method

        // Stubbing methods
        doReturn(false).when(comment).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        when(modalTrackerService.isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class))).thenReturn(false);
        when(commentMentionMapper.toEntity(any(User.class), any(User.class), any(Comment.class), any(NotificationStatus.class))).thenReturn(expected);
        when(mentionRepository.save(any(CommentMention.class))).thenReturn(expected);

        // Calling the method
        // Assertions
        CommentMention actual = commentMentionService.mention(mentioningUser, mentionedUser, comment);
        assertNotNull(actual);
        assertEquals(expected, actual);

        // Behavior Verifications

        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(modalTrackerService).isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class));
        verify(commentMentionMapper).toEntity(any(User.class), any(User.class), any(Comment.class), any(NotificationStatus.class));
        verify(mentionRepository).save(any(CommentMention.class));
    }
}
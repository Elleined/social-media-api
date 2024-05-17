package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.mapper.mention.ReplyMentionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.service.mt.ModalTrackerService;
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
class ReplyMentionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private MentionRepository mentionRepository;
    @Mock
    private ReplyMentionMapper replyMentionMapper;

    @InjectMocks
    private ReplyMentionService replyMentionService;


    @Test
    void mention() {
        // Expected Value

        // Mock data
        User mentioningUser = new User();
        User mentionedUser = new User();
        Reply reply = spy(Reply.class);
        reply.setComment(Comment.builder()
                .id(1)
                .build());

        ReplyMention expected = new ReplyMention();

        // Set up method

        // Stubbing methods
        doReturn(false).when(reply).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        when(modalTrackerService.isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class))).thenReturn(false);
        when(replyMentionMapper.toEntity(any(User.class), any(User.class), any(Reply.class), any(NotificationStatus.class))).thenReturn(expected);
        when(mentionRepository.save(any(ReplyMention.class))).thenReturn(expected);

        // Calling the method
        // Assertions
        ReplyMention actual = replyMentionService.mention(mentioningUser, mentionedUser, reply);
        assertNotNull(actual);
        assertEquals(expected, actual);

        // Behavior Verifications

        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(modalTrackerService).isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class));
        verify(replyMentionMapper).toEntity(any(User.class), any(User.class), any(Reply.class), any(NotificationStatus.class));
        verify(mentionRepository).save(any(ReplyMention.class));
    }
}
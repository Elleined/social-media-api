package com.elleined.forumapi.service.mention;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.mapper.mention.ReplyMentionMapper;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
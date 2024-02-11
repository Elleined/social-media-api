package com.elleined.forumapi.service.mention;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.mapper.mention.PostMentionMapper;
import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostMentionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private PostMentionMapper postMentionMapper;
    @Mock
    private MentionRepository mentionRepository;

    @InjectMocks
    private PostMentionService postMentionService;

    @Test
    void mention() {
        // Expected Value

        // Mock data
        User mentioningUser = new User();
        User mentionedUser = new User();
        Post post = spy(Post.class);

        PostMention expected = new PostMention();

        // Set up method

        // Stubbing methods
        doReturn(false).when(post).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        when(modalTrackerService.isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class))).thenReturn(false);
        when(postMentionMapper.toEntity(any(User.class), any(User.class), any(Post.class), any(NotificationStatus.class))).thenReturn(expected);
        when(mentionRepository.save(any(PostMention.class))).thenReturn(expected);

        // Calling the method
        // Assertions
        PostMention actual = postMentionService.mention(mentioningUser, mentionedUser, post);
        assertNotNull(actual);
        assertEquals(expected, actual);

        // Behavior Verifications

        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(modalTrackerService).isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class));
        verify(postMentionMapper).toEntity(any(User.class), any(User.class), any(Post.class), any(NotificationStatus.class));
        verify(mentionRepository).save(any(PostMention.class));
    }
}
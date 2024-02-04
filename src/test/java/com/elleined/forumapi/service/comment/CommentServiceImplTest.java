package com.elleined.forumapi.service.comment;

import com.elleined.forumapi.MultiPartFileDataFactory;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import com.elleined.forumapi.service.hashtag.entity.CommentHashTagService;
import com.elleined.forumapi.service.mention.CommentMentionService;
import com.elleined.forumapi.service.pin.PostPinCommentService;
import com.elleined.forumapi.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private PostPinCommentService postPinCommentService;
    @Mock
    private CommentMentionService commentMentionService;
    @Mock
    private CommentHashTagService commentHashTagService;

    @Mock
    private Validator validator;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    void save() {
        // Expected and Actual Value
        // Mock Data
        User currentUser = spy(User.class);

        Post post = spy(Post.class);
        post.setAuthor(new User());

        String commentBody = "Valid comment body";

        MultipartFile attachedPicture = MultiPartFileDataFactory.notEmpty();

        Set<User> mentionedUsers = new HashSet<>();
        mentionedUsers.add(new User());

        Set<String> keywords = new HashSet<>();
        keywords.add("Keyword1");

        // Stubbing methods
        doReturn(false).when(validator).isNotValid(anyString());
        doReturn(false).when(post).isCommentSectionClosed();
        doReturn(false).when(post).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(modalTrackerService.isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class))).thenReturn(true);
        when(commentMapper.toEntity(anyString(), any(Post.class), any(User.class), anyString(), any(NotificationStatus.class))).thenReturn(new Comment());
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());
        doReturn(true).when(validator).isValid(anySet());
        doNothing().when(commentMentionService).mentionAll(any(User.class), anySet(), any(Comment.class));
        when(commentHashTagService.saveAll(any(Comment.class), anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        // Assertions
        assertNotNull(attachedPicture.getOriginalFilename());

        assertDoesNotThrow(() -> commentService.save(currentUser, post, commentBody, attachedPicture, mentionedUsers, keywords));

        // Behavior Verifications

        verify(validator).isNotValid(anyString());
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(modalTrackerService).isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class));
        verify(commentMapper).toEntity(anyString(), any(Post.class), any(User.class), anyString(), any(NotificationStatus.class));
        verify(commentRepository).save(any(Comment.class));
        verify(validator, atMost(2)).isValid(anySet());
        verify(commentMentionService).mentionAll(any(User.class), anySet(), any(Comment.class));
        verify(commentHashTagService).saveAll(any(Comment.class), anySet());
    }

    @Test
    void delete() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getAllByPost() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getById() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void updateBody() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getTotalReplies() {
        // Expected and Actual Value

        // Mock Data

        // Stubbing methods

        // Calling the method

        // Assertions

        // Behavior Verifications
    }
}
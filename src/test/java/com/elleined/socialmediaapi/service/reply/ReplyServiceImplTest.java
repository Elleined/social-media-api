package com.elleined.socialmediaapi.service.reply;

import com.elleined.socialmediaapi.MultiPartFileDataFactory;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.service.main.reply.ReplyServiceImpl;
import com.elleined.socialmediaapi.service.mt.ModalTrackerService;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.entity.ReplyHashTagService;
import com.elleined.socialmediaapi.service.mention.ReplyMentionService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceImplTest {

    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private ReplyMapper replyMapper;
    @Mock
    private ModalTrackerService modalTrackerService;
    @Mock
    private BlockService blockService;
    @Mock
    private CommentPinReplyService commentPinReplyService;
    @Mock
    private ReplyMentionService replyMentionService;
    @Mock
    private ReplyHashTagService replyHashTagService;
    @Mock
    private Validator validator;

    @InjectMocks
    private ReplyServiceImpl replyService;

    @Test
    void save() throws IOException {
        // Expected Value

        // Mock data
        User currentUser = new User();

        Comment comment = spy(Comment.class);
        comment.setCommenter(new User());

        String body = "Reply Body";
        MultipartFile attachmentPicture = MultiPartFileDataFactory.notEmpty();
        Set<User> mentionedUsers = new HashSet<>();
        Set<String> keywords = new HashSet<>();

        Reply reply = new Reply();

        // Set up method

        // Stubbing methods
        when(validator.isNotValid(anyString())).thenReturn(false);
        doReturn(false).when(comment).isCommentSectionClosed();
        doReturn(false).when(comment).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(modalTrackerService.isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class))).thenReturn(false);
        when(replyMapper.toEntity(anyString(), any(User.class), any(Comment.class), anyString(), any(NotificationStatus.class))).thenReturn(reply);
        when(validator.isValid(anyCollection())).thenReturn(true);
        doNothing().when(replyMentionService).mentionAll(any(User.class), anySet(), any(Reply.class));
        when(replyHashTagService.saveAll(any(Reply.class), anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        Reply actualReply = replyService.save(currentUser, comment, body, attachmentPicture, mentionedUsers, keywords);

        // Behavior Verifications
        verify(validator).isNotValid(anyString());
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(modalTrackerService).isModalOpen(anyInt(), anyInt(), any(ModalTracker.Type.class));
        verify(replyMapper).toEntity(anyString(), any(User.class), any(Comment.class), anyString(), any(NotificationStatus.class));
        verify(validator, atMost(2)).isValid(anyCollection());
        verify(replyMentionService).mentionAll(any(User.class), anySet(), any(Reply.class));
        verify(replyHashTagService).saveAll(any(Reply.class), anySet());

        // Assertions
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Comment comment = spy(Comment.class);

        Reply reply = Reply.builder()
                .status(Status.ACTIVE)
                .build();

        // Set up method
        comment.setPinnedReply(reply);

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Reply.class));
        doReturn(false).when(comment).doesNotHave(any(Reply.class));
        when(replyRepository.save(any(Reply.class))).thenReturn(reply);
        doAnswer(i -> {
            comment.setPinnedReply(null);
            return comment;
        }).when(commentPinReplyService).unpin(any(Comment.class));

        // Calling the method
        replyService.delete(currentUser, comment, reply);

        // Behavior Verifications
        verify(replyRepository).save(any(Reply.class));
        verify(commentPinReplyService).unpin(any(Comment.class));

        // Assertions
        assertEquals(Status.INACTIVE, reply.getStatus());
        assertNull(comment.getPinnedReply());
    }

    @Test
    void updateBody() {
        // Expected Value
        String oldBody = "Old Body";
        String newBody = "New Body";

        // Mock data
        User currentUser = spy(User.class);

        Reply reply = Reply.builder()
                .body(oldBody)
                .build();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Reply.class));
        when(replyRepository.save(any(Reply.class))).thenReturn(reply);

        // Calling the method
        replyService.updateBody(currentUser, reply, newBody);

        // Behavior Verifications
        verify(replyRepository).save(any(Reply.class));

        // Assertions
        assertEquals(newBody, reply.getBody());
    }

    @Test
    void getAllByComment() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Comment comment = spy(Comment.class);

        Reply pinnedReply = Reply.builder()
                .replier(new User())
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now().plusMinutes(4))
                .build();

        Reply activeReply1 = Reply.builder()
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now().plusMinutes(1))
                .replier(new User())
                .build();

        Reply activeReply2 = Reply.builder()
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now().plusMinutes(2))
                .replier(new User())
                .build();

        Reply inActiveReply = Reply.builder()
                .status(Status.INACTIVE)
                .replier(new User())
                .build();

        // Set up method
        List<Reply> rawReplies = List.of(inActiveReply, activeReply1, activeReply2, pinnedReply);

        comment.setReplies(rawReplies);
        comment.setPinnedReply(pinnedReply);

        List<Reply> expectedReplies = List.of(pinnedReply, activeReply2, activeReply1);

        // Stubbing methods
        doReturn(false).when(comment).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        List<Reply> actualReplies = replyService.getAllByComment(currentUser, comment);

        // Behavior Verifications
        verify(blockService, atLeastOnce()).isBlockedBy(any(User.class), any(User.class));
        verify(blockService, atLeastOnce()).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertIterableEquals(expectedReplies, actualReplies);
    }
}
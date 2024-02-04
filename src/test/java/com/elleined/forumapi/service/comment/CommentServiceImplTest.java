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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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
        User currentUser = spy(User.class);

        Comment comment = new Comment();
        List<Reply> replies = Arrays.asList(
                Reply.builder()
                        .status(Status.ACTIVE)
                        .build(),
                Reply.builder()
                        .status(Status.ACTIVE)
                        .build()
        );
        comment.setStatus(Status.ACTIVE);
        comment.setReplies(replies);

        Post post = spy(Post.class);
        post.setPinnedComment(comment);

        // Stubbing methods
        doReturn(false).when(post).doesNotHave(any(Comment.class));
        doReturn(false).when(currentUser).notOwned(any(Comment.class));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        doAnswer(answer -> {
            post.setPinnedComment(null);
            return post;
        }).when(postPinCommentService).unpin(any(Comment.class));
        when(replyRepository.saveAll(anyList())).thenReturn(replies);

        // Calling the method
        assertDoesNotThrow(() -> commentService.delete(currentUser, post, comment));

        // Assertions
        assertEquals(Status.INACTIVE, comment.getStatus());
        assertNull(post.getPinnedComment());
        assertTrue(comment.isInactive());
        assertTrue(comment.getReplies().stream().allMatch(Reply::isInactive));

        // Behavior Verifications
        verify(commentRepository).save(any(Comment.class));
        verify(postPinCommentService).unpin(any(Comment.class));
        verify(replyRepository).saveAll(anyList());
    }

    @Test
    void getAllByPost() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);

        Comment pinnedComment = Comment.builder()
                .status(Status.ACTIVE)
                .commenter(new User())
                .build();

        Comment activeComment1 = Comment.builder()
                .status(Status.ACTIVE)
                .commenter(new User())
                .upvotingUsers(Set.of(new User(), new User()))
                .build();

        Comment activeComment2 = Comment.builder()
                .status(Status.ACTIVE)
                .commenter(new User())
                .upvotingUsers(Set.of(new User(), new User(), new User()))
                .build();

        Comment inactiveComment = Comment.builder()
                .status(Status.INACTIVE)
                .commenter(new User())
                .upvotingUsers(Set.of(new User(), new User(), new User(), new User()))
                .build();

        List<Comment> rawComments = Arrays.asList(inactiveComment, activeComment1, activeComment2, pinnedComment);

        Post post = spy(Post.class);
        post.setComments(rawComments);
        post.setPinnedComment(pinnedComment);

        List<Comment> expectedComments = Arrays.asList(pinnedComment, activeComment2, activeComment1);

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);

        // Calling the method
        // Assertions
        assertIterableEquals(expectedComments, commentService.getAllByPost(currentUser, post));

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
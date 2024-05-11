package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.mapper.react.CommentReactionMapper;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.react.CommentReactRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentReactionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private CommentReactRepository commentReactRepository;
    @Mock
    private CommentReactionMapper commentReactionMapper;

    @InjectMocks
    private CommentReactionService commentReactionService;

    @Test
    void getByUserReaction() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        CommentReact expectedCommentReact = CommentReact.commentReactBuilder()
                .respondent(currentUser)
                .build();

        CommentReact otherUserCommentReact = CommentReact.commentReactBuilder()
                .respondent(new User())
                .build();

        Comment comment = Comment.builder()
                .reactions(List.of(expectedCommentReact, otherUserCommentReact))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        CommentReact actualCommentReact = commentReactionService.getByUserReaction(currentUser, comment);

        // Behavior Verifications

        // Assertions
        assertEquals(expectedCommentReact, actualCommentReact);
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        CommentReact commentReact1 = CommentReact.commentReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        CommentReact commentReact2 = CommentReact.commentReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Comment comment = Comment.builder()
                .reactions(List.of(commentReact1, commentReact2))
                .build();

        // Set up method
        List<CommentReact> expectedCommentReacts = List.of(commentReact2, commentReact1);

        // Stubbing methods

        // Calling the method
        List<CommentReact> actualCommentReacts = commentReactionService.getAll(comment);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedCommentReacts,  actualCommentReacts);
    }

    @ParameterizedTest
    @ValueSource(strings = { "LIKE", "HEART", "CARE", "HAHA", "WOW", "SAD", "ANGRY"})
    void getAllReactionByEmojiType(String emojiType) {
        // Expected Value

        // Mock data
        CommentReact expectedReact1 = CommentReact.commentReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        CommentReact expectedReact2 = CommentReact.commentReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Comment comment = Comment.builder()
                .reactions(List.of(expectedReact1, expectedReact2))
                .build();

        // Set up method

        // Stubbing methods
        List<CommentReact> expectedReacts = List.of(expectedReact2, expectedReact1);

        // Calling the method
        List<CommentReact> actualReacts = commentReactionService.getAllReactionByEmojiType(comment, Emoji.Type.valueOf(emojiType));

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedReacts, actualReacts);
    }

    @Test
    void save() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        Comment comment = spy(Comment.class);
        comment.setCommenter(new User());

        Emoji emoji = new Emoji();

        CommentReact commentReact = new CommentReact();

        // Set up method
        doReturn(false).when(comment).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(commentReactionMapper.toEntity(any(User.class), any(Comment.class), any(Emoji.class))).thenReturn(commentReact);
        when(commentReactRepository.save(any(CommentReact.class))).thenReturn(commentReact);

        // Stubbing methods

        // Calling the method
        commentReactionService.save(currentUser, comment, emoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(commentReactionMapper).toEntity(any(User.class), any(Comment.class), any(Emoji.class));
        verify(commentReactRepository).save(any(CommentReact.class));

        // Assertions
    }

    @Test
    void update() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Comment comment = spy(Comment.class);
        comment.setCommenter(new User());

        Emoji expectedEmoji = Emoji.builder()
                .type(Emoji.Type.ANGRY)
                .build();

        CommentReact commentReact = CommentReact.commentReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.HAHA)
                        .build())
                .build();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(CommentReact.class));
        doReturn(false).when(comment).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(commentReactRepository.save(any(CommentReact.class))).thenReturn(commentReact);

        // Calling the method
        commentReactionService.update(currentUser, comment, commentReact, expectedEmoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertEquals(expectedEmoji, commentReact.getEmoji());
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Comment comment = new Comment();
        CommentReact commentReact = new CommentReact();


        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(CommentReact.class));
        doNothing().when(commentReactRepository).delete(any(CommentReact.class));

        // Calling the method
        commentReactionService.delete(currentUser, commentReact);

        // Behavior Verifications
        verify(commentReactRepository).delete(any(CommentReact.class));

        // Assertions
    }
}

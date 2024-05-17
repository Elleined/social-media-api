package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.mapper.react.ReplyReactionMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.react.ReplyReactRepository;
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
class ReplyReactionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private ReplyReactRepository replyReactRepository;
    @Mock
    private ReplyReactionMapper replyReactionMapper;

    @InjectMocks
    private ReplyReactionService replyReactionService;

    @Test
    void getByUserReaction() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        ReplyReact expectedReplyReact = ReplyReact.replyReactBuilder()
                .respondent(currentUser)
                .build();

        ReplyReact otherUserReplyReact = ReplyReact.replyReactBuilder()
                .respondent(new User())
                .build();

        Reply reply = Reply.builder()
                .reactions(List.of(expectedReplyReact, otherUserReplyReact))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        ReplyReact actualReplyReact = replyReactionService.getByUserReaction(currentUser, reply);

        // Behavior Verifications

        // Assertions
        assertEquals(expectedReplyReact, actualReplyReact);
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        ReplyReact replyReact1 = ReplyReact.replyReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        ReplyReact replyReact2 = ReplyReact.replyReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Reply reply = Reply.builder()
                .reactions(List.of(replyReact1, replyReact2))
                .build();

        // Set up method
        List<ReplyReact> expectedReplyReacts = List.of(replyReact2, replyReact1);

        // Stubbing methods

        // Calling the method
        List<ReplyReact> actualReplyReacts = replyReactionService.getAll(reply);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedReplyReacts, actualReplyReacts);
    }

    @ParameterizedTest
    @ValueSource(strings = { "LIKE", "HEART", "CARE", "HAHA", "WOW", "SAD", "ANGRY"})
    void getAllReactionByEmojiType(String emojiType) {
        // Expected Value

        // Mock data
        ReplyReact expectedReact1 = ReplyReact.replyReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        ReplyReact expectedReact2 = ReplyReact.replyReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Reply reply = Reply.builder()
                .reactions(List.of(expectedReact1, expectedReact2))
                .build();

        // Set up method

        // Stubbing methods
        List<ReplyReact> expectedReacts = List.of(expectedReact2, expectedReact1);

        // Calling the method
        List<ReplyReact> actualReacts = replyReactionService.getAllReactionByEmojiType(reply, Emoji.Type.valueOf(emojiType));

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedReacts, actualReacts);
    }

    @Test
    void save() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        Reply reply = spy(Reply.class);
        reply.setReplier(new User());

        Emoji emoji = new Emoji();

        ReplyReact replyReact = new ReplyReact();

        // Set up method
        doReturn(false).when(reply).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(replyReactionMapper.toEntity(any(User.class), any(Reply.class), any(Emoji.class))).thenReturn(replyReact);
        when(replyReactRepository.save(any(ReplyReact.class))).thenReturn(replyReact);

        // Stubbing methods

        // Calling the method
        replyReactionService.save(currentUser, reply, emoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(replyReactionMapper).toEntity(any(User.class), any(Reply.class), any(Emoji.class));
        verify(replyReactRepository).save(any(ReplyReact.class));

        // Assertions
    }

    @Test
    void update() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Reply reply = spy(Reply.class);
        reply.setReplier(new User());

        Emoji expectedEmoji = Emoji.builder()
                .type(Emoji.Type.ANGRY)
                .build();

        ReplyReact replyReact = ReplyReact.replyReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.HAHA)
                        .build())
                .build();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(ReplyReact.class));
        doReturn(false).when(reply).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(replyReactRepository.save(any(ReplyReact.class))).thenReturn(replyReact);

        // Calling the method
        replyReactionService.update(currentUser, reply, replyReact, expectedEmoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertEquals(expectedEmoji, replyReact.getEmoji());
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Reply reply = new Reply();
        ReplyReact replyReact = new ReplyReact();


        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(ReplyReact.class));
        doNothing().when(replyReactRepository).delete(any(ReplyReact.class));

        // Calling the method
        replyReactionService.delete(currentUser, replyReact);

        // Behavior Verifications
        verify(replyReactRepository).delete(any(ReplyReact.class));

        // Assertions
    }

}
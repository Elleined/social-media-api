package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.mapper.react.PostReactionMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.repository.react.PostReactRepository;
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
class PostReactionServiceTest {

    @Mock
    private BlockService blockService;
    @Mock
    private PostReactRepository postReactRepository;
    @Mock
    private PostReactionMapper postReactionMapper;

    @InjectMocks
    private PostReactionService postReactionService;

    @Test
    void getByUserReaction() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        PostReact expectedPostReact = PostReact.postReactBuilder()
                .respondent(currentUser)
                .build();

        PostReact otherUserPostReact = PostReact.postReactBuilder()
                .respondent(new User())
                .build();

        Post post = Post.builder()
                .reactions(List.of(expectedPostReact, otherUserPostReact))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        PostReact actualPostReact = postReactionService.getByUserReaction(currentUser, post);

        // Behavior Verifications

        // Assertions
        assertEquals(expectedPostReact, actualPostReact);
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        PostReact postReact1 = PostReact.postReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        PostReact postReact2 = PostReact.postReactBuilder()
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Post post = Post.builder()
                .reactions(List.of(postReact1, postReact2))
                .build();

        // Set up method
        List<PostReact> expectedPostReacts = List.of(postReact2, postReact1);

        // Stubbing methods

        // Calling the method
        List<PostReact> actualPostReacts = postReactionService.getAll(post);

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedPostReacts, actualPostReacts);
    }

    @ParameterizedTest
    @ValueSource(strings = { "LIKE", "HEART", "CARE", "HAHA", "WOW", "SAD", "ANGRY"})
    void getAllReactionByEmojiType(String emojiType) {
        // Expected Value

        // Mock data
        PostReact expectedReact1 = PostReact.postReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(1))
                .build();

        PostReact expectedReact2 = PostReact.postReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.valueOf(emojiType))
                        .build())
                .createdAt(LocalDateTime.now().plusMinutes(2))
                .build();

        Post post = Post.builder()
                .reactions(List.of(expectedReact1, expectedReact2))
                .build();

        // Set up method

        // Stubbing methods
        List<PostReact> expectedReacts = List.of(expectedReact2, expectedReact1);

        // Calling the method
        List<PostReact> actualReacts = postReactionService.getAllReactionByEmojiType(post, Emoji.Type.valueOf(emojiType));

        // Behavior Verifications

        // Assertions
        assertIterableEquals(expectedReacts, actualReacts);
    }

    @Test
    void save() {
        // Expected Value

        // Mock data
        User currentUser = new User();

        Post post = spy(Post.class);
        post.setAuthor(new User());

        Emoji emoji = new Emoji();

        PostReact postReact = new PostReact();

        // Set up method
        doReturn(false).when(post).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(postReactionMapper.toEntity(any(User.class), any(Post.class), any(Emoji.class))).thenReturn(postReact);
        when(postReactRepository.save(any(PostReact.class))).thenReturn(postReact);

        // Stubbing methods

        // Calling the method
        postReactionService.save(currentUser, post, emoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(postReactionMapper).toEntity(any(User.class), any(Post.class), any(Emoji.class));
        verify(postReactRepository).save(any(PostReact.class));

        // Assertions
    }

    @Test
    void update() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        Post post = spy(Post.class);
        post.setAuthor(new User());

        Emoji expectedEmoji = Emoji.builder()
                .type(Emoji.Type.ANGRY)
                .build();

        PostReact postReact = PostReact.postReactBuilder()
                .emoji(Emoji.builder()
                        .type(Emoji.Type.HAHA)
                        .build())
                .build();

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(PostReact.class));
        doReturn(false).when(post).isInactive();
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(postReactRepository.save(any(PostReact.class))).thenReturn(postReact);

        // Calling the method
        postReactionService.update(currentUser, post, postReact, expectedEmoji);

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));

        // Assertions
        assertEquals(expectedEmoji, postReact.getEmoji());
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Post post = new Post();
        PostReact postReact = new PostReact();


        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(PostReact.class));
        doNothing().when(postReactRepository).delete(any(PostReact.class));

        // Calling the method
        postReactionService.delete(currentUser, postReact);

        // Behavior Verifications
        verify(postReactRepository).delete(any(PostReact.class));

        // Assertions
    }

}
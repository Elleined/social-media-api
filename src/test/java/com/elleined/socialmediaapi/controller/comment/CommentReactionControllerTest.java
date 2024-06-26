package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.emoji.EmojiService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.reaction.ReactionService;
import com.elleined.socialmediaapi.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CommentReactionController.class, properties = {"server.port=5454"})
class CommentReactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private ReactionService reactionService;
    @MockBean
    private ReactionMapper reactionMapper;
    @MockBean
    private EmojiService emojiService;


    @Test
    void getAll() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(reactionService.getAll(any(User.class), any(Post.class), any(Comment.class), any(Pageable.class))).thenReturn(List.of(new Reaction()));
        when(reactionMapper.toDTO(any(Reaction.class))).thenReturn(new ReactionDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions", 1, 1, 1)
                        .param("pageNumber", "1")
                        .param("pageSize", "5")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "id"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(reactionService).getAll(any(User.class), any(Post.class), any(Comment.class), any(Pageable.class));
        verify(reactionMapper, atLeastOnce()).toDTO(any(Reaction.class));

        // Assertions
    }

    @Test
    void getAllByEmoji() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(emojiService.getById(anyInt())).thenReturn(new Emoji());
        when(reactionService.getAllByEmoji(any(User.class), any(Post.class), any(Comment.class), any(Emoji.class), any(Pageable.class))).thenReturn(List.of(new Reaction()));
        when(reactionMapper.toDTO(any(Reaction.class))).thenReturn(new ReactionDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions/emoji", 1, 1, 1)
                        .param("emojiId", String.valueOf(1))
                        .param("pageNumber", "1")
                        .param("pageSize", "5")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "id"))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(emojiService).getById(anyInt());
        verify(reactionService).getAllByEmoji(any(User.class), any(Post.class), any(Comment.class), any(Emoji.class), any(Pageable.class));
        verify(reactionMapper, atLeastOnce()).toDTO(any(Reaction.class));

        // Assertions
    }

    @Test
    void shouldInvokeSaveIfNotAlreadyVoted() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(emojiService.getById(anyInt())).thenReturn(new Emoji());
        when(reactionService.isAlreadyReactedTo(any(User.class), any(Post.class), any(Comment.class))).thenReturn(false);
        when(reactionService.save(any(User.class), any(Post.class), any(Comment.class), any(Emoji.class))).thenReturn(new Reaction());
        when(reactionMapper.toDTO(any(Reaction.class))).thenReturn(new ReactionDTO());

        // Calling the method
        mockMvc.perform(post("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions", 1, 1, 1)
                        .param("emojiId", String.valueOf(1)))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(emojiService).getById(anyInt());
        verify(reactionService).isAlreadyReactedTo(any(User.class), any(Post.class), any(Comment.class));
        verify(reactionService).save(any(User.class), any(Post.class), any(Comment.class), any(Emoji.class));
        verify(reactionMapper).toDTO(any(Reaction.class));

        // Assertions
    }

    @Test
    void shouldInvokeTheUpdateIfAlreadyVoted() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(emojiService.getById(anyInt())).thenReturn(new Emoji());
        when(reactionService.isAlreadyReactedTo(any(User.class), any(Post.class), any(Comment.class))).thenReturn(true);
        when(reactionService.getByUserReaction(any(User.class), any(Post.class), any(Comment.class))).thenReturn(new Reaction());
        doNothing().when(reactionService).update(any(User.class), any(Post.class), any(Comment.class), any(Reaction.class), any(Emoji.class));
        when(reactionMapper.toDTO(any(Reaction.class))).thenReturn(new ReactionDTO());

        // Calling the method
        mockMvc.perform(post("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions", 1, 1, 1)
                        .param("emojiId", String.valueOf(1)))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(emojiService).getById(anyInt());
        verify(reactionService).isAlreadyReactedTo(any(User.class), any(Post.class), any(Comment.class));
        verify(reactionService).getByUserReaction(any(User.class), any(Post.class), any(Comment.class));
        verify(reactionService).update(any(User.class), any(Post.class), any(Comment.class), any(Reaction.class), any(Emoji.class));
        verify(reactionMapper).toDTO(any(Reaction.class));

        // Assertions
    }

    @Test
    void deleted() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(reactionService.getById(anyInt())).thenReturn(new Reaction());
        doNothing().when(reactionService).delete(any(User.class), any(Post.class), any(Comment.class), any(Reaction.class));

        // Calling the method
        mockMvc.perform(delete("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactions/{reactionId}", 1, 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(reactionService).getById(anyInt());
        verify(reactionService).delete(any(User.class), any(Post.class), any(Comment.class), any(Reaction.class));

        // Assertions
    }
}
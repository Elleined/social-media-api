package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.vote.VoteDTO;
import com.elleined.socialmediaapi.mapper.vote.VoteMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.vote.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentVoteController.class)
class CommentVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private VoteService voteService;
    @MockBean
    private VoteMapper voteMapper;
    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;


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
        when(voteService.getAll(any(User.class), any(Post.class), any(Comment.class), any(Pageable.class))).thenReturn(List.of(new Vote()));
        when(voteMapper.toDTO(any(Vote.class))).thenReturn(new VoteDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes", 1, 1, 1)
                        .param())
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(voteService).getAll(any(User.class), any(Post.class), any(Comment.class), any(Pageable.class));
        verify(voteMapper, atLeastOnce()).toDTO(any(Vote.class));

        // Assertions
    }

    @Test
    void getById() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(voteService.getById(anyInt())).thenReturn(new Vote());
        when(voteMapper.toDTO(any(Vote.class))).thenReturn(new VoteDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes/{voteId}", 1, 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(voteService).getById(anyInt());
        verify(voteMapper).toDTO(any(Vote.class));

        // Assertions
    }

    @Test
    void getAllById() throws Exception {
        // Pre defined values
        List<Integer> ids = List.of(1, 2);
        // Expected Value

        // Mock data

        // Set up method
        String json = objectMapper.writeValueAsString(ids);

        // Stubbing methods
        when(voteService.getAllById(anyList())).thenReturn(List.of(new Vote()));
        when(voteMapper.toDTO(any(Vote.class))).thenReturn(new VoteDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes/get-all-by-id", 1, 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(voteService).getAllById(anyList());
        verify(voteMapper, atLeastOnce()).toDTO(any(Vote.class));

        // Assertions
    }

    @Test
    void getAllByVerdict() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(voteService.getAll(any(User.class), any(Post.class), any(Comment.class), any(Vote.Verdict.class), )).thenReturn(List.of(new Vote()));
        when(voteMapper.toDTO(any(Vote.class))).thenReturn(new VoteDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes/verdict", 1, 1, 1)
                        .param("verdict", Vote.Verdict.UP_VOTE.name()))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(voteService).getAll(any(User.class), any(Post.class), any(Comment.class), any(Vote.Verdict.class), );
        verify(voteMapper, atLeastOnce()).toDTO(any(Vote.class));

        // Assertions
    }

    @Test
    void save() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(voteService.save(any(User.class), any(Post.class), any(Comment.class), any(Vote.Verdict.class))).thenReturn(new Vote());
        when(voteMapper.toDTO(any(Vote.class))).thenReturn(new VoteDTO());

        // Calling the method
        mockMvc.perform(post("/users/{currentUserId}/posts/{postId}/comments/{commentId}/votes", 1, 1, 1)
                        .param("verdict", Vote.Verdict.UP_VOTE.name()))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(voteService).save(any(User.class), any(Post.class), any(Comment.class), any(Vote.Verdict.class));
        verify(voteMapper, atLeastOnce()).toDTO(any(Vote.class));

        // Assertions
    }
}
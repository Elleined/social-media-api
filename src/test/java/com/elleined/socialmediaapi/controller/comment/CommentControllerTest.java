package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.MultipartDataProvider;
import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.service.ws.WSService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private PostService postService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private ReplyService replyService;
    @MockBean
    private HashTagService hashTagService;
    @MockBean
    private WSService wsService;

    @Test
    void getAll() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getAll(any(User.class), any(Post.class))).thenReturn(List.of(new Comment()));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments", 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getAll(any(User.class), any(Post.class));
        verify(commentMapper, atLeastOnce()).toDTO(any(Comment.class));

        // Assertions
    }

    @Test
    void getById() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/{id}", 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(commentService).getById(anyInt());
        verify(commentMapper).toDTO(any(Comment.class));

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
        when(commentService.getAllById(anyList())).thenReturn(List.of(new Comment()));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());

        // Calling the method
        mockMvc.perform(get("/users/{currentUserId}/posts/{postId}/comments/get-all-by-id", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(commentService).getAllById(anyList());
        verify(commentMapper).toDTO(any(Comment.class));

        // Assertions
    }

    @Test
    void save() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data
        String body = "Body";
        MockMultipartFile attachedPicture = MultipartDataProvider.getPicture("attachedPicture");
        Set<Integer> mentionedUserIds = Set.of(1, 2);
        Set<Integer> hashTagIds = Set.of(1, 2);


        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(userService.getAllById(anyList())).thenReturn(List.of(new User()));
        when(hashTagService.getAllById(anyList())).thenReturn(List.of(new HashTag()));
        when(commentService.save(any(User.class), any(Post.class), anyString(), any(MultipartFile.class), anySet(), anySet())).thenReturn(new Comment());
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());
        doNothing().when(wsService).broadcast(any(CommentDTO.class));

        // Calling the method
        mockMvc.perform(multipart(HttpMethod.POST, "/users/{currentUserId}/posts/{postId}/comments", 1, 1)
                        .file(new MockMultipartFile("body", body.getBytes()))
                        .file(attachedPicture)
                        .file(new MockMultipartFile("mentionedUserIds", "", "application/json", objectMapper.writeValueAsBytes(mentionedUserIds)))
                        .file(new MockMultipartFile("hashTagIds", "", "application/json", objectMapper.writeValueAsBytes(hashTagIds))))
                .andExpect(status().isOk());
        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(userService).getAllById(anyList());
        verify(hashTagService).getAllById(anyList());
        verify(commentService).save(any(User.class), any(Post.class), anyString(), any(MultipartFile.class), anySet(), anySet());
        verify(commentMapper).toDTO(any(Comment.class));
        verify(wsService).broadcast(any(CommentDTO.class));

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
        when(commentService.getById(anyInt())).thenReturn(Comment.builder()
                .replies(List.of(new Reply()))
                .build());
        doNothing().when(commentService).delete(any(User.class), any(Post.class), any(Comment.class));
        doNothing().when(replyService).delete(any(User.class), any(Post.class), any(Comment.class), any(Reply.class));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());
        doNothing().when(wsService).broadcast(any(CommentDTO.class));

        // Calling the method
        mockMvc.perform(delete("/users/{currentUserId}/posts/{postId}/comments/{commentId}", 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(commentService).delete(any(User.class), any(Post.class), any(Comment.class));
        verify(replyService, atLeastOnce()).delete(any(User.class), any(Post.class), any(Comment.class), any(Reply.class));
        verify(commentMapper).toDTO(any(Comment.class));
        verify(wsService).broadcast(any(CommentDTO.class));

        // Assertions
    }

    @Test
    void update() throws Exception {
        // Pre defined values
        String newBody = "New Body";
        MockMultipartFile newAttachedPicture = MultipartDataProvider.getPicture("newAttachedPicture");

        // Expected Value

        // Mock data

        // Set up method
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        doNothing().when(commentService).update(any(User.class), any(Post.class), any(Comment.class), anyString(), any(MultipartFile.class));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());
        doNothing().when(wsService).broadcast(any(CommentDTO.class));

        // Stubbing methods

        // Calling the method
        mockMvc.perform(multipart(HttpMethod.PUT, "/users/{currentUserId}/posts/{postId}/comments/{commentId}", 1, 1, 1)
                        .file("newBody", newBody.getBytes())
                        .file(newAttachedPicture))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(commentService).update(any(User.class), any(Post.class), any(Comment.class), anyString(), any(MultipartFile.class));
        verify(commentMapper).toDTO(any(Comment.class));
        verify(wsService).broadcast(any(CommentDTO.class));

        // Assertions
    }

    @Test
    void reactivate() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(postService.getById(anyInt())).thenReturn(new Post());
        when(commentService.getById(anyInt())).thenReturn(Comment.builder()
                .replies(List.of(new Reply()))
                .build());
        doNothing().when(commentService).reactivate(any(User.class), any(Post.class), any(Comment.class));
        doNothing().when(replyService).reactivate(any(User.class), any(Post.class), any(Comment.class), any(Reply.class));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());

        // Calling the method
        mockMvc.perform(patch("/users/{currentUserId}/posts/{postId}/comments/{commentId}/reactivate", 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(postService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(commentService).reactivate(any(User.class), any(Post.class), any(Comment.class));
        verify(replyService).reactivate(any(User.class), any(Post.class), any(Comment.class), any(Reply.class));
        verify(commentMapper).toDTO(any(Comment.class));

        // Assertions
    }
}
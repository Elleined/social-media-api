package com.elleined.socialmediaapi.controller.comment;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.reply.ReplyService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentPinReplyController.class)
class CommentPinReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private CommentPinReplyService commentPinReplyService;
    @MockBean
    private ReplyService replyService;

    @Test
    void pinReply() throws Exception {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods
        when(userService.getById(anyInt())).thenReturn(new User());
        when(commentService.getById(anyInt())).thenReturn(new Comment());
        when(replyService.getById(anyInt())).thenReturn(new Reply());
        doNothing().when(commentPinReplyService).pin(any(User.class), any(Comment.class), any(Reply.class));
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(new CommentDTO());

        // Calling the method
        mockMvc.perform(patch("/users/{currentUserId}/posts/{postId}/comments/{commentId}/pin-reply/{replyId}", 1, 1, 1, 1))
                .andExpect(status().isOk());

        // Behavior Verifications
        verify(userService).getById(anyInt());
        verify(commentService).getById(anyInt());
        verify(replyService).getById(anyInt());
        verify(commentPinReplyService).pin(any(User.class), any(Comment.class), any(Reply.class));
        verify(commentMapper).toDTO(any(Comment.class));

        // Assertions
    }
}
package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.CommentDTO;
import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WSServiceTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private ReplyMapper replyMapper;

    @InjectMocks
    private WSService wsService;

    @Test
    void broadcastComment() {
        // Expected Value

        // Mock data
        CommentDTO commentDTO = CommentDTO.builder()
                .body("Body")
                .build();

        // Set up method
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(commentDTO);
        doNothing().when(simpMessagingTemplate).convertAndSend(anyString(), any(CommentDTO.class));

        // Stubbing methods

        // Calling the method
        wsService.broadcast(new Comment());

        // Behavior Verifications
        verify(commentMapper).toDTO(any(Comment.class));
        verify(simpMessagingTemplate).convertAndSend(anyString(), any(CommentDTO.class));

        // Assertions
    }

    @Test
    void broadcastReply() {
        // Expected Value

        // Mock data
        ReplyDTO replyDTO = ReplyDTO.builder()
                .body("Body")
                .build();

        // Set up method
        when(replyMapper.toDTO(any(Reply.class))).thenReturn(replyDTO);
        doNothing().when(simpMessagingTemplate).convertAndSend(anyString(), any(ReplyDTO.class));

        // Stubbing methods

        // Calling the method
        wsService.broadcast(new Reply());

        // Behavior Verifications
        verify(replyMapper).toDTO(any(Reply.class));
        verify(simpMessagingTemplate).convertAndSend(anyString(), any(ReplyDTO.class));

        // Assertions
    }
}
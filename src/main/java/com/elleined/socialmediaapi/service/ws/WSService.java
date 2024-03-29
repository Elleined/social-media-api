package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.CommentDTO;
import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Reply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;

    public void broadcast(Comment comment) {
        CommentDTO commentDTO = commentMapper.toDTO(comment);
        commentDTO.setBody(HtmlUtils.htmlEscape(commentDTO.getBody()));

        final String destination = "/discussion/posts/" + commentDTO.getPostId() + "/comments";
        simpMessagingTemplate.convertAndSend(destination, commentDTO);
        log.debug("Comment with id of {} and body of {} broadcast successfully to {}", commentDTO.getId(), commentDTO.getBody(), destination);
    }

    public void broadcast(Reply reply) {
        ReplyDTO replyDTO = replyMapper.toDTO(reply);
        replyDTO.setBody(HtmlUtils.htmlEscape(replyDTO.getBody()));

        final String destination = "/discussion/posts/comments/" + replyDTO.getCommentId() + "/replies";
        simpMessagingTemplate.convertAndSend(destination, replyDTO);
        log.debug("Reply with id of {} and body of {} broadcast successfully to {}", replyDTO.getId(), replyDTO.getBody(), destination);
    }
}

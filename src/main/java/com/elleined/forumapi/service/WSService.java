package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.model.Comment;
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

    void broadcastComment(CommentDTO commentDTO) {
        commentDTO.setBody(HtmlUtils.htmlEscape(commentDTO.getBody()));

        final String destination = "/discussion/posts/" + commentDTO.getPostId() + "/comments";
        simpMessagingTemplate.convertAndSend(destination, commentDTO);
        log.debug("Comment with id of {} and body of {} broadcast successfully to {}", commentDTO.getId(), commentDTO.getBody(), destination);
    }

    void broadcastReply(ReplyDTO replyDTO) {
        replyDTO.setBody(HtmlUtils.htmlEscape(replyDTO.getBody()));

        final String destination = "/discussion/posts/comments/" + replyDTO.getCommentId() + "/replies";
        simpMessagingTemplate.convertAndSend(destination, replyDTO);
        log.debug("Reply with id of {} and body of {} broadcast successfully to {}", replyDTO.getId(), replyDTO.getBody(), destination);
    }
}

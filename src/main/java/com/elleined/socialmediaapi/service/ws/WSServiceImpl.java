package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSServiceImpl implements WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcastOnComment(CommentDTO commentDTO) {
        commentDTO.setBody(HtmlUtils.htmlEscape(commentDTO.getBody()));
        int postId = commentDTO.getPostId();
        int commentId = commentDTO.getId();

        final String destination = STR."/sma/posts/\{commentDTO.getPostId()}/comments";
        simpMessagingTemplate.convertAndSend(destination, commentDTO);
        log.debug("Broadcasting comment with id of {} in post with id of {} success", commentId, postId);
    }

    @Override
    public void broadcastOnReply(ReplyDTO replyDTO) {
        replyDTO.setBody(HtmlUtils.htmlEscape(replyDTO.getBody()));
        int postId = replyDTO.getPostId();
        int commentId = replyDTO.getCommentId();
        int replyId = replyDTO.getId();

        final String destination = STR."/sma/posts\{postId}/comments/\{replyDTO.getCommentId()}/replies";
        simpMessagingTemplate.convertAndSend(destination, replyDTO);
        log.debug("Broadcasting reply with id of {} to comment with id of {} success", replyId, commentId);
    }
}

package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class WSServiceImpl implements WSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void broadcast(CommentDTO commentDTO) {
        commentDTO.setBody(HtmlUtils.htmlEscape(commentDTO.getBody()));

        final String destination = "/discussion/posts/" + commentDTO.getPostId() + "/comments";
        simpMessagingTemplate.convertAndSend(destination, commentDTO);
        log.debug("Comment with id of {} and body of {} broadcast successfully to {}", commentDTO.getId(), commentDTO.getBody(), destination);
    }

    @Override
    public void broadcast(ReplyDTO replyDTO) {
        replyDTO.setBody(HtmlUtils.htmlEscape(replyDTO.getBody()));

        final String destination = "/discussion/posts/comments/" + replyDTO.getCommentId() + "/replies";
        simpMessagingTemplate.convertAndSend(destination, replyDTO);
        log.debug("Reply with id of {} and body of {} broadcast successfully to {}", replyDTO.getId(), replyDTO.getBody(), destination);
    }

    @Override
    public void broadcast(NotificationDTO notificationDTO) {
        throw new NotYetImplementedException("Please dont use this this is not yet implemented!");
    }
}

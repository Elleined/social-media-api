package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;

public interface WSService {
    void broadcast(CommentDTO commentDTO);

    void broadcast(ReplyDTO replyDTO);

    void  broadcast(NotificationDTO notificationDTO);
}

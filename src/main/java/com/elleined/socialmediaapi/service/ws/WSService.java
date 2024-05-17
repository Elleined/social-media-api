package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;

public interface WSService {
    void broadcast(Comment comment);

    void broadcast(Reply reply);
}

package com.elleined.socialmediaapi.service.ws;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.main.ReplyDTO;

public interface WSService {
    void broadcast(CommentDTO commentDTO);

    void broadcast(ReplyDTO replyDTO);
}

package com.elleined.socialmediaapi.ws;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.dto.main.ReplyDTO;

public interface WSService {
    void broadcastOnComment(CommentDTO commentDTO);

    void broadcastOnReply(ReplyDTO replyDTO);
}

package com.elleined.socialmediaapi.service.notification.reply.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.User;

public interface ReplyNotificationReaderService {
    void readAll(User currentUser, Comment comment);
}

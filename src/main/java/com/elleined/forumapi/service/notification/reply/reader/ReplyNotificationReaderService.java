package com.elleined.forumapi.service.notification.reply.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;

public interface ReplyNotificationReaderService {
    void readAll(User currentUser, Comment comment);
}

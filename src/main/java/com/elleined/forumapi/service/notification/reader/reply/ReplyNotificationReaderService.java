package com.elleined.forumapi.service.notification.reader.reply;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.User;

public interface ReplyNotificationReaderService {
    void readAll(User currentUser, Comment comment);
}

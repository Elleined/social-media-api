package com.elleined.forumapi.service.notification.reader.comment;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

public interface CommentNotificationReaderService {
    void readAll(User currentUser, Post post);
}

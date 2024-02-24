package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;

public interface CommentNotificationReaderService {
    void readAll(User currentUser, Post post);
}

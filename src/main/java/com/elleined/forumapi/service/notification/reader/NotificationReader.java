package com.elleined.forumapi.service.notification.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;

public interface NotificationReader {
    void read(User currentUser);
    void read(User currentUser, Post post);
    void read(User currentUser, Comment comment);
}

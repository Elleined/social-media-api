package com.elleined.forumapi.service.notification.comment;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.notification.NotificationService;

public interface CommentNotificationService extends NotificationService<Comment> {
    int notificationCountForCommenter(User author, Post post, User commenter);
}

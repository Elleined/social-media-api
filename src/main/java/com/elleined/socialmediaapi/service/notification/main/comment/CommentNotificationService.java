package com.elleined.socialmediaapi.service.notification.main.comment;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface CommentNotificationService extends NotificationService<CommentNotification> {
    CommentNotification save(User creator, Comment comment);
}

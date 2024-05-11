package com.elleined.socialmediaapi.service.notification.comment;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;

public interface CommentNotificationService extends NotificationService<Comment> {

    @Override
    List<Comment> getAllUnreadNotification(User currentUser);

    default int notificationCountForCommenter(User currentUser, Post post, User commenter) {
        return (int) getAllUnreadNotification(currentUser).stream()
                .filter(comment -> comment.getPost().equals(post))
                .filter(comment -> comment.getCommenter().equals(commenter))
                .count();
    }
}

package com.elleined.forumapi.service.notification.comment;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.notification.NotificationService;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collection;
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

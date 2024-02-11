package com.elleined.forumapi.service.notification.reply;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.notification.NotificationService;

import java.util.List;

public interface ReplyNotificationService extends NotificationService<Reply> {
    @Override
    List<Reply> getAllUnreadNotification(User currentUser);

    int notificationCountForReplier(User commenter, Comment comment, User replier);
}

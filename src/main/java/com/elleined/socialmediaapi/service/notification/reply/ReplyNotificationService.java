package com.elleined.socialmediaapi.service.notification.reply;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;

public interface ReplyNotificationService extends NotificationService<Reply> {
    @Override
    List<Reply> getAllUnreadNotification(User currentUser);

    int notificationCountForReplier(User commenter, Comment comment, User replier);
}

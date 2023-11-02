package com.elleined.forumapi.service.notification.reply;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.service.notification.NotificationService;

public interface ReplyNotificationService extends NotificationService<Reply> {
    int notificationCountForReplier(User commenter, Comment comment, User replier);
}

package com.elleined.socialmediaapi.service.notification.main.reply;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface ReplyNotificationService extends NotificationService<ReplyNotification> {
    ReplyNotification save(User creator, Reply reply);
}

package com.elleined.forumapi.service.notification.reader;

import com.elleined.forumapi.service.notification.reader.comment.CommentNotificationReaderService;
import com.elleined.forumapi.service.notification.reader.post.PostNotificationReaderService;
import com.elleined.forumapi.service.notification.reader.reply.ReplyNotificationReaderService;

public interface NotificationReader extends PostNotificationReaderService,
        CommentNotificationReaderService,
        ReplyNotificationReaderService {
}

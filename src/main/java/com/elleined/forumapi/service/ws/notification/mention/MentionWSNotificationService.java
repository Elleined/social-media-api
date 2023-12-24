package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.Mention;
import com.elleined.forumapi.service.ws.notification.WSNotificationService;

import java.util.Set;

public interface MentionWSNotificationService<T extends Mention> extends WSNotificationService<T> {
    String MENTION_NOTIFICATION_DESTINATION = "/notification/mentions/";

    void broadcastMentions(Set<T> mentions);
}

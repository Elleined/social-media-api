package com.elleined.forumapi.service.ws.notification.mention;

import com.elleined.forumapi.model.mention.Mention;
import com.elleined.forumapi.service.ws.notification.WSNotificationService;

import java.util.Set;

public interface WSMentionNotificationService<T extends Mention> extends WSNotificationService<T> {
    String MENTION_NOTIFICATION_DESTINATION = "/notification/mentions/";

    default void broadcastMentions(Set<T> mentions) {
        mentions.forEach(this::broadcast);
    }
}

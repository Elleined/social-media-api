package com.elleined.forumapi.service.ws.notification.like;

import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.service.ws.notification.WSNotificationService;

public interface LikeWSNotificationService<T extends Like> extends WSNotificationService<T> {
    String LIKE_NOTIFICATION_DESTINATION = "/notification/likes/";


}

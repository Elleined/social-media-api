package com.elleined.forumapi.mapper.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.Mention;

public interface MentionMapper<T> {

    Mention toEntity(User mentioningUser,
                     User mentionedUser,
                     T t,
                     NotificationStatus notificationStatus);
}

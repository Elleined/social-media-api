package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.mention.Mention;

public interface MentionMapper<T> {

    Mention toEntity(User mentioningUser,
                     User mentionedUser,
                     T t,
                     NotificationStatus notificationStatus);
}

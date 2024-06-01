package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.notification.reaction.ReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface ReactionNotificationService<T extends ReactionNotification,
        S extends PrimaryKeyIdentity> extends NotificationService<T> {
    T save(User currentUser, S s, Reaction reaction);
}

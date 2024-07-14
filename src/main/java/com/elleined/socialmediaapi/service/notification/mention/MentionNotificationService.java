package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.mention.MentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.notification.NotificationService;

import java.util.List;
import java.util.Set;

public interface MentionNotificationService<T extends MentionNotification,
        S extends PrimaryKeyIdentity> extends NotificationService<T> {

    T save(User currentUser, Mention mention, S s);


    default List<T> saveAll(User currentUser, Set<Mention> mentions, S s) {
        return mentions.stream()
                .map(mention -> this.save(currentUser, mention, s))
                .toList();
    }
}

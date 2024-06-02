package com.elleined.socialmediaapi.service.notification.vote;

import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import com.elleined.socialmediaapi.service.notification.NotificationService;

public interface VoteNotificationService extends NotificationService<VoteNotification> {
    VoteNotification save(User currentUser, Vote vote);
}

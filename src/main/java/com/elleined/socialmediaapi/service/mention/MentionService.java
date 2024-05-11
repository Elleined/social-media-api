package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.mention.Mention;

import java.util.Set;

public interface MentionService<T> {
    Mention mention(User mentioningUser, User mentionedUser, T t);
    void mentionAll(User mentioningUser, Set<User> mentionedUsers, T t);
}

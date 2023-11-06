package com.elleined.forumapi.service;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.Mention;

import java.util.Set;

public interface MentionService<T> {
    Mention mention(User mentioningUser, User mentionedUser, T t);
    void mentionAll(User mentioningUser, Set<User> mentionedUsers, T t);
}

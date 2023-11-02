package com.elleined.forumapi.service.mention;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.Mention;

import java.util.List;
import java.util.Set;

public interface MentionService<MENTION extends Mention, T>  {
    MENTION mention(User mentioningUser, User mentionedUser, T t);
    List<MENTION> mentionAll(User mentioningUser, Set<User> mentionedUsers, T t);
}

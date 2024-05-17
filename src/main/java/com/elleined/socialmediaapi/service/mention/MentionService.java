package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;

public interface MentionService {
    Mention save(User mentioningUser, User mentionedUser);

    default List<Mention> saveAll(User mentioningUser, Set<User> mentionedUsers) {
        return mentionedUsers.stream()
                .map(mentionedUser -> this.save(mentionedUser, mentionedUser))
                .toList();
    }
}

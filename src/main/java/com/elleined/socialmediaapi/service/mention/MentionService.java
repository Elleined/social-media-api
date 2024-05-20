package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.Set;
import java.util.stream.Collectors;

public interface MentionService extends CustomService<Mention> {
    Mention save(User mentioningUser, User mentionedUser);

    default Set<Mention> saveAll(User mentioningUser, Set<User> mentionedUsers) {
        if (mentionedUsers.isEmpty()) return null;
        return mentionedUsers.stream()
                .map(mentionedUser -> this.save(mentioningUser, mentionedUser))
                .collect(Collectors.toSet());
    }
}

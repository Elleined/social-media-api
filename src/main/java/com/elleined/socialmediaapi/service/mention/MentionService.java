package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MentionService extends CustomService<Mention> {
    Mention save(User mentioningUser, User mentionedUser);
    List<User> getAllSuggestedMentions(User currentUser, String name);


    default Set<Mention> saveAll(User mentioningUser, Set<User> mentionedUsers) {
        return mentionedUsers.stream()
                .map(mentionedUser -> this.save(mentioningUser, mentionedUser))
                .collect(Collectors.toSet());
    }
}

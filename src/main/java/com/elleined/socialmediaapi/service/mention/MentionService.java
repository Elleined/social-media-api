package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MentionService {
    Mention save(User mentioningUser, User mentionedUser);
    List<Mention> getAllById(Set<Integer> ids);

    default Set<Mention> saveAll(User mentioningUser, Set<User> mentionedUsers) {
        return mentionedUsers.stream()
                .map(mentionedUser -> this.save(mentioningUser, mentionedUser))
                .collect(Collectors.toSet());
    }

    List<User> getSuggestedMentions(User currentUser, String name);
}

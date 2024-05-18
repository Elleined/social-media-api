package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.model.user.User;

public interface StoryServiceRestriction {

    default boolean hasStory(User currentUser) {
        return currentUser.getStory() != null;
    }

    default boolean doesNotHaveStory(User currentUser) {
        return currentUser.getStory() == null;
    }
}

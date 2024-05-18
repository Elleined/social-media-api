package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.story.StoryRequest;
import com.elleined.socialmediaapi.service.CustomService;

public interface StoryService extends CustomService<Story> {
    Story save(User currentUser, StoryRequest storyRequest);
    void delete(User currentUser, Story story);
    Story getStory(User currentUser);

    void deleteAllExpiredStory();
}

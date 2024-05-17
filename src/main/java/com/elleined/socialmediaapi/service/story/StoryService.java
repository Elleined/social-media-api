package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.story.StoryRequest;

import java.util.List;
import java.util.Set;

public interface StoryService {
    Story save(User currentUser, StoryRequest storyRequest);
    List<Story> getAll();
    List<Story> getAllById(Set<Integer> ids);
    void delete(User currentUser, Story story);
    Story getStory(User currentUser);

    void deleteAllExpiredStory();
}

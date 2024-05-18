package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.story.StoryRequest;

import java.util.List;

public interface StoryService {
    Story save(User currentUser, StoryRequest storyRequest);
    Story getById(int id) throws ResourceNotFoundException;
    List<Story> getAll();
    List<Story> getAllById(List<Integer> ids);
    void delete(User currentUser, Story story);
    Story getStory(User currentUser);

    void deleteAllExpiredStory();
}

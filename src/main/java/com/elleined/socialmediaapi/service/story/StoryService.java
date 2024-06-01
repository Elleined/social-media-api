package com.elleined.socialmediaapi.service.story;

import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface StoryService extends CustomService<Story> {
    Story save(User currentUser,
               String content,
               MultipartFile attachedPicture,
               Set<Mention> mentions);

    void delete(User currentUser, Story story);
    Story getStory(User currentUser);

    void deleteAllExpiredStory();
}

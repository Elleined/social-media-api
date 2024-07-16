package com.elleined.socialmediaapi.controller.user.story;

import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.notification.mention.MentionNotificationService;
import com.elleined.socialmediaapi.service.story.StoryService;
import com.elleined.socialmediaapi.service.user.UserService;
import com.elleined.socialmediaapi.ws.notification.NotificationWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{currentUserId}/story")
public class UserStoryController {
    private final UserService userService;

    private final StoryService storyService;
    private final StoryMapper storyMapper;

    private final MentionService mentionService;

    private final MentionNotificationService<StoryMentionNotification, Story> mentionNotificationService;
    private final MentionNotificationMapper mentionNotificationMapper;

    private final NotificationWSService notificationWSService;


    @GetMapping
    public StoryDTO getStory(@PathVariable("currentUserId") int currentUserId) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getStory(currentUser);
        return storyMapper.toDTO(story);
    }

    @PostMapping
    public StoryDTO save(@PathVariable("currentUserId") int currentUserId,
                         @RequestPart("content") String content,
                         @RequestPart(required = false, name = "attachedPicture") MultipartFile attachedPicture,
                         @RequestPart(required = false, name = "mentionedUserIds") Set<Integer> mentionedUserIds) {

        // Getting entities
        User currentUser = userService.getById(currentUserId);

        // Saving entities
        Set<Mention> mentions = mentionService.saveAll(currentUser, userService.getAllById(mentionedUserIds));
        Story story = storyService.save(currentUser, content, attachedPicture, mentions);
        List<StoryMentionNotification> storyMentionNotifications = mentionNotificationService.saveAll(currentUser, mentions, story);

        // DTO Conversion
        StoryDTO storyDTO = storyMapper.toDTO(story);
        List<StoryMentionNotificationDTO> storyMentionNotificationDTOS = storyMentionNotifications.stream()
                .map(mentionNotificationMapper::toDTO)
                .toList();

        // Web Socket
        storyMentionNotificationDTOS.forEach(notificationWSService::notifyOnMentioned);

        return storyDTO;
    }

    @DeleteMapping("/{storyId}")
    public void delete(@PathVariable("currentUserId") int currentUserId,
                       @PathVariable("storyId") int storyId) {
        User currentUser = userService.getById(currentUserId);
        Story story = storyService.getById(storyId);
        storyService.delete(currentUser, story);
    }
}

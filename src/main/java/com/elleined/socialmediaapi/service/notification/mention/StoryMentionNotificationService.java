package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.mention.StoryMentionNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class StoryMentionNotificationService implements MentionNotificationService<StoryMentionNotification, Story> {

    private final UserRepository userRepository;

    private final StoryMentionNotificationRepository storyMentionNotificationRepository;
    private final MentionNotificationMapper mentionNotificationMapper;

    @Override
    public Page<StoryMentionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllStoryMentionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public StoryMentionNotification getById(int id) throws ResourceNotFoundException {
        return storyMentionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, StoryMentionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        storyMentionNotificationRepository.save(notification);
    }

    @Override
    public StoryMentionNotification save(User currentUser, Mention mention, Story story) {
        StoryMentionNotification storyMentionNotification = mentionNotificationMapper.toEntity(currentUser, mention, story);

        storyMentionNotificationRepository.save(storyMentionNotification);
        log.debug("Saving story mention notification success");
        return storyMentionNotification;
    }
}

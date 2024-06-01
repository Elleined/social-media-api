package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.reaction.StoryReactionNotificationRepository;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoryReactionNotificationService implements ReactionNotificationService<StoryReactionNotification, Story>{
    private final UserRepository userRepository;

    private final StoryReactionNotificationRepository storyReactionNotificationRepository;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @Override
    public StoryReactionNotification save(User currentUser, Story story, Reaction reaction) {
        StoryReactionNotification storyReactionNotification = reactionNotificationMapper.toEntity(currentUser, story, reaction);

        storyReactionNotificationRepository.save(storyReactionNotification);
        log.debug("Saving story reaction notification success");
        return storyReactionNotification;
    }

    @Override
    public List<StoryReactionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllStoryReactionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public StoryReactionNotification getById(int id) throws ResourceNotFoundException {
        return storyReactionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, StoryReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        storyReactionNotificationRepository.save(notification);
    }
}

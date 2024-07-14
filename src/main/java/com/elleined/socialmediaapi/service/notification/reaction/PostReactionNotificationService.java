package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.PostReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.reaction.PostReactionNotificationRepository;
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
public class PostReactionNotificationService implements ReactionNotificationService<PostReactionNotification, Post>{
    private final PostReactionNotificationRepository postReactionNotificationRepository;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @Override
    public PostReactionNotification save(User currentUser, Post post, Reaction reaction) {
        PostReactionNotification postReactionNotification = reactionNotificationMapper.toEntity(currentUser, post, reaction);

        postReactionNotificationRepository.save(postReactionNotification);
        log.debug("Saving post reaction notification success");
        return postReactionNotification;
    }

    @Override
    public Page<PostReactionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return postReactionNotificationRepository.findAll(currentUser, status, pageable);
    }

    @Override
    public PostReactionNotification getById(int id) throws ResourceNotFoundException {
        return postReactionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, PostReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        postReactionNotificationRepository.save(notification);
    }

    @Override
    public void unread(User currentUser, PostReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot unread notification! because current user doesn't owned this notification!");

        notification.unread();
        postReactionNotificationRepository.save(notification);
    }
}

package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.mention.PostMentionNotificationRepository;
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
public class PostMentionNotificationService implements MentionNotificationService<PostMentionNotification, Post> {
    private final UserRepository userRepository;

    private final PostMentionNotificationRepository postMentionNotificationRepository;
    private final MentionNotificationMapper mentionNotificationMapper;

    @Override
    public List<PostMentionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllPostMentionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public PostMentionNotification getById(int id) throws ResourceNotFoundException {
        return postMentionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, PostMentionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        postMentionNotificationRepository.save(notification);
    }

    @Override
    public PostMentionNotification save(User currentUser, Mention mention, Post post) {
        PostMentionNotification postMentionNotification = mentionNotificationMapper.toEntity(currentUser, mention, post);

        postMentionNotificationRepository.save(postMentionNotification);
        log.debug("Saving post mention notification success");
        return postMentionNotification;
    }
}

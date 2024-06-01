package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.MentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PostMentionNotificationService implements MentionNotificationService<PostMentionNotification> {
    private final UserRepository userRepository;

    private final MentionNotificationMapper mentionNotificationMapper;

    @Override
    public List<PostMentionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return List.of();
    }

    @Override
    public PostMentionNotification getById(int id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void read(User currentUser, PostMentionNotification notification) {

    }
}

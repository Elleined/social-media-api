package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.mention.CommentMentionNotificationRepository;
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
public class CommentMentionNotificationService implements MentionNotificationService<CommentMentionNotification, Comment> {
    private final CommentMentionNotificationRepository commentMentionNotificationRepository;
    private final MentionNotificationMapper mentionNotificationMapper;

    @Override
    public Page<CommentMentionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return commentMentionNotificationRepository.findAll(currentUser, status, pageable);
    }

    @Override
    public CommentMentionNotification getById(int id) throws ResourceNotFoundException {
        return commentMentionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, CommentMentionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        commentMentionNotificationRepository.save(notification);
    }

    @Override
    public void unread(User currentUser, CommentMentionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot unread notification! because current user doesn't owned this notification!");

        notification.unread();
        commentMentionNotificationRepository.save(notification);
    }

    @Override
    public CommentMentionNotification save(User currentUser, Mention mention, Comment comment) {
        CommentMentionNotification commentMentionNotification = mentionNotificationMapper.toEntity(currentUser, mention, comment);

        commentMentionNotificationRepository.save(commentMentionNotification);
        log.debug("Saving comment mention notification success");
        return commentMentionNotification;
    }
}

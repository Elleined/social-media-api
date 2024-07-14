package com.elleined.socialmediaapi.service.notification.main.comment;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.main.CommentNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.main.CommentNotificationRepository;
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
public class CommentNotificationServiceImpl implements CommentNotificationService {
    private final CommentNotificationRepository commentNotificationRepository;
    private final CommentNotificationMapper commentNotificationMapper;

    @Override
    public CommentNotification save(User creator, Comment comment) {
        CommentNotification commentNotification = commentNotificationMapper.toEntity(creator, comment);

        commentNotificationRepository.save(commentNotification);
        log.debug("Saving comment notification success");
        return commentNotification;
    }
    
    @Override
    public Page<CommentNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return commentNotificationRepository.findAll(currentUser, status, pageable);
    }

    @Override
    public CommentNotification getById(int id) throws ResourceNotFoundException {
        return commentNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, CommentNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        commentNotificationRepository.save(notification);
    }

    @Override
    public void unread(User currentUser, CommentNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot unread notification! because current user doesn't owned this notification!");

        notification.unread();
        commentNotificationRepository.save(notification);
    }
}

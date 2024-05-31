package com.elleined.socialmediaapi.service.notification.main.comment;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.CommentNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.main.CommentNotificationRepository;
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
public class CommentNotificationServiceImpl implements CommentNotificationService {
    private final UserRepository userRepository;

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
    public List<CommentNotification> getAll(User currentUser, Pageable pageable) {
        return userRepository.findAllReceiveCommentNotifications(currentUser, pageable).getContent();
    }

    @Override
    public CommentNotification getById(int id) throws ResourceNotFoundException {
        return commentNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(CommentNotification notification) {
        notification.read();
        commentNotificationRepository.save(notification);
    }
}

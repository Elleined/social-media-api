package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.reaction.CommentReactionNotificationRepository;
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
public class CommentReactionNotificationService implements ReactionNotificationService<CommentReactionNotification, Comment> {
    private final UserRepository userRepository;

    private final CommentReactionNotificationRepository commentReactionNotificationRepository;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @Override
    public List<CommentReactionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllCommentReactionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public CommentReactionNotification getById(int id) throws ResourceNotFoundException {
        return commentReactionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, CommentReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        commentReactionNotificationRepository.save(notification);
    }

    @Override
    public CommentReactionNotification save(User currentUser, Comment comment, Reaction reaction) {
        CommentReactionNotification commentReactionNotification = reactionNotificationMapper.toEntity(currentUser, comment, reaction);

        commentReactionNotificationRepository.save(commentReactionNotification);
        log.debug("Saving comment reaction notification success");
        return commentReactionNotification;
    }
}

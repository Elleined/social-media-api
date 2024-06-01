package com.elleined.socialmediaapi.service.notification.reaction;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.reaction.ReactionNotificationMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.reaction.ReplyReactionNotificationRepository;
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
public class ReplyReactionNotificationService implements ReactionNotificationService<ReplyReactionNotification, Reply> {
    private final UserRepository userRepository;

    private final ReplyReactionNotificationRepository replyReactionNotificationRepository;
    private final ReactionNotificationMapper reactionNotificationMapper;

    @Override
    public ReplyReactionNotification save(User currentUser, Reply reply, Reaction reaction) {
        ReplyReactionNotification replyReactionNotification = reactionNotificationMapper.toEntity(currentUser, reply, reaction);

        replyReactionNotificationRepository.save(replyReactionNotification);
        log.debug("Saving reply reaction notification success");
        return replyReactionNotification;
    }

    @Override
    public List<ReplyReactionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllReplyReactionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public ReplyReactionNotification getById(int id) throws ResourceNotFoundException {
        return replyReactionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, ReplyReactionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        replyReactionNotificationRepository.save(notification);
    }
}

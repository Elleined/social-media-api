package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.mention.MentionNotificationMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.mention.ReplyMentionNotificationRepository;
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
public class ReplyMentionNotificationService implements MentionNotificationService<ReplyMentionNotification, Reply> {
    private final UserRepository userRepository;

    private final ReplyMentionNotificationRepository replyMentionNotificationRepository;
    private final MentionNotificationMapper mentionNotificationMapper;

    @Override
    public List<ReplyMentionNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return userRepository.findAllReplyMentionNotifications(currentUser, pageable).stream()
                .filter(notification -> notification.getStatus() == status)
                .toList();
    }

    @Override
    public ReplyMentionNotification getById(int id) throws ResourceNotFoundException {
        return replyMentionNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mention notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, ReplyMentionNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        replyMentionNotificationRepository.save(notification);
    }

    @Override
    public ReplyMentionNotification save(User currentUser, Mention mention, Reply reply) {
        ReplyMentionNotification replyMentionNotification = mentionNotificationMapper.toEntity(currentUser, mention, reply);

        replyMentionNotificationRepository.save(replyMentionNotification);
        log.debug("Saving reply mention notification success");
        return replyMentionNotification;
    }
}

package com.elleined.socialmediaapi.service.notification.main.reply;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.notification.main.ReplyNotificationMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.main.ReplyNotificationRepository;
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
public class ReplyNotificationServiceImpl implements ReplyNotificationService {
    private final ReplyNotificationRepository replyNotificationRepository;
    private final ReplyNotificationMapper replyNotificationMapper;

    @Override
    public ReplyNotification save(User creator, Reply reply) {
        ReplyNotification replyNotification = replyNotificationMapper.toEntity(creator, reply);

        replyNotificationRepository.save(replyNotification);
        log.debug("Saving reply notification success");
        return replyNotification;
    }
    
    @Override
    public Page<ReplyNotification> getAll(User currentUser, Notification.Status status, Pageable pageable) {
        return replyNotificationRepository.findAll(currentUser, status, pageable);
    }

    @Override
    public ReplyNotification getById(int id) throws ResourceNotFoundException {
        return replyNotificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification with id of " + id + " doesn't exists!"));
    }

    @Override
    public void read(User currentUser, ReplyNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot read notification! because current user doesn't owned this notification!");

        notification.read();
        replyNotificationRepository.save(notification);
    }

    @Override
    public void unread(User currentUser, ReplyNotification notification) {
        if (!currentUser.has(notification))
            throw new ResourceNotFoundException("Cannot unread notification! because current user doesn't owned this notification!");

        notification.unread();
        replyNotificationRepository.save(notification);
    }
}

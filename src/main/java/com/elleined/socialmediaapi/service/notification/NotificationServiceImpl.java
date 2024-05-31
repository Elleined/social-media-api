package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.mapper.notification.CommentNotificationMapper;
import com.elleined.socialmediaapi.mapper.notification.ReplyNotificationMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.notification.main.CommentNotificationRepository;
import com.elleined.socialmediaapi.repository.notification.main.ReplyNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final CommentNotificationRepository commentNotificationRepository;
    private final CommentNotificationMapper commentNotificationMapper;

    private final ReplyNotificationRepository replyNotificationRepository;
    private final ReplyNotificationMapper replyNotificationMapper;


    @Override
    public List<List<? extends Notification>> getAll(User currentUser) {
        return List.of(
                currentUser.getCommentNotifications(),
                currentUser.getReplyNotifications()
        );
    }

    @Override
    public CommentNotification saveOnComment(User creator, Comment comment) {
        CommentNotification commentNotification = commentNotificationMapper.toEntity(creator, comment);

        commentNotificationRepository.save(commentNotification);
        log.debug("Saving comment notification success");
        return commentNotification;
    }

    @Override
    public ReplyNotification saveOnReply(User creator, Reply reply) {
        ReplyNotification replyNotification = replyNotificationMapper.toEntity(creator, reply);

        replyNotificationRepository.save(replyNotification);
        log.debug("Saving reply notification success");
        return replyNotification;
    }
}

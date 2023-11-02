package com.elleined.forumapi.service.notification.reply;

import com.elleined.forumapi.model.*;
import com.elleined.forumapi.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyNotificationServiceImpl implements ReplyNotificationService {
    private final BlockService blockService;

    @Override
    public List<Reply> getAllUnreadNotification(User currentUser) {
        return currentUser.getComments().stream()
                .map(Comment::getReplies)
                .flatMap(replies -> replies.stream()
                        .filter(reply -> reply.getStatus() == Status.ACTIVE)
                        .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                        .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                        .filter(reply -> reply.getNotificationStatus() == NotificationStatus.UNREAD))
                .toList();
    }

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }

    @Override
    public int notificationCountForReplier(User commenter, Comment comment, User replier) {
        return (int) comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> reply.getNotificationStatus() == NotificationStatus.UNREAD)
                .filter(reply -> !blockService.isBlockedBy(commenter, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(commenter, reply.getReplier()))
                .filter(reply -> reply.getReplier().equals(replier))
                .count();
    }
}

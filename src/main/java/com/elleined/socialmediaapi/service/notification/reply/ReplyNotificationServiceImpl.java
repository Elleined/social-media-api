package com.elleined.socialmediaapi.service.notification.reply;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.block.BlockService;
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
                        .filter(Reply::isActive)
                        .filter(Reply::isUnread))
                        .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                        .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .toList();
    }

    @Override
    public int notificationCountForReplier(User commenter, Comment comment, User replier) {
        return (int) comment.getReplies()
                .stream()
                .filter(Reply::isActive)
                .filter(Reply::isUnread)
                .filter(reply -> reply.getReplier().equals(replier))
                .filter(reply -> !blockService.isBlockedBy(commenter, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(commenter, reply.getReplier()))
                .count();
    }
}

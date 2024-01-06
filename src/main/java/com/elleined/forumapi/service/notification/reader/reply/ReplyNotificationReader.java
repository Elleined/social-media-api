package com.elleined.forumapi.service.notification.reader.reply;

import com.elleined.forumapi.model.*;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Primary
public class ReplyNotificationReader implements ReplyNotificationReaderService {
    private final BlockService blockService;
    private final ReplyRepository replyRepository;

    @Override
    public void readAll(User currentUser, Comment comment) {
        if (!currentUser.equals(comment.getCommenter())) {
            log.trace("Will not mark as unread because the current user with id of {} are not the commenter of the comment {}", currentUser.getId(), comment.getCommenter().getId());
            return;
        }
        log.trace("Will mark all as read because the current user with id of {} is the commenter of the comment {}", currentUser.getId(), comment.getCommenter().getId());
        List<Reply> replies = comment.getReplies()
                .stream()
                .filter(Reply::isActive)
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .toList();

        replies.forEach(reply -> reply.setNotificationStatus(NotificationStatus.READ));
        replyRepository.saveAll(replies);
        log.debug("Replies in comment with id of {} read successfully!", comment.getId());
    }
}

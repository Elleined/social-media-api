package com.elleined.forumapi.service.notification.reply.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.notification.reply.ReplyNotificationService;
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
    private final ReplyNotificationService replyNotificationService;
    private final ReplyRepository replyRepository;

    @Override
    public void readAll(User currentUser, Comment comment) {
        List<Reply> replies = replyNotificationService.getAllUnreadNotification(currentUser);
        replies.stream()
                .filter(reply -> reply.getComment().equals(comment))
                .forEach(reply -> reply.setNotificationStatus(NotificationStatus.READ));
        replyRepository.saveAll(replies);
        log.debug("Replies in comment with id of {} read successfully!", comment.getId());
    }
}

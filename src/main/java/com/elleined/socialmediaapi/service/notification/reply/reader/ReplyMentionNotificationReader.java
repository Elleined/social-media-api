package com.elleined.socialmediaapi.service.notification.reply.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.MentionRepository;
import com.elleined.socialmediaapi.service.notification.mention.ReplyMentionNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Qualifier("replyMentionNotificationReader")
public class ReplyMentionNotificationReader implements ReplyNotificationReaderService {
    private final ReplyMentionNotificationService replyMentionNotificationService;
    private final MentionRepository mentionRepository;

    @Override
    public void readAll(User currentUser, Comment comment) {
        List<ReplyMention> receiveUnreadReplyMentions = replyMentionNotificationService.getAllUnreadNotification(currentUser);
        receiveUnreadReplyMentions.stream()
                .filter(replyMention -> replyMention.getReply().getComment().equals(comment))
                .forEach(replyMention -> replyMention.setNotificationStatus(NotificationStatus.READ));

        mentionRepository.saveAll(receiveUnreadReplyMentions);
        log.debug("Reading all reply mentions for current user with id of {} success", currentUser.getId());
    }
}

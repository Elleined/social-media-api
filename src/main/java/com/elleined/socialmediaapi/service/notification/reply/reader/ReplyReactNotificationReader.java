package com.elleined.socialmediaapi.service.notification.reply.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.react.ReplyReactRepository;
import com.elleined.socialmediaapi.service.notification.react.ReplyReactNotificationService;
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
@Qualifier("replyReactNotificationReader")
public class ReplyReactNotificationReader implements ReplyNotificationReaderService {
    private final ReplyReactNotificationService replyReactNotificationService;
    private final ReplyReactRepository replyReactRepository;

    @Override
    public void readAll(User currentUser, Comment comment) {
        List<ReplyReact> unreadReactions = replyReactNotificationService.getAllUnreadNotification(currentUser);
        unreadReactions.stream()
                .filter(replyReact -> replyReact.getReply().getComment().equals(comment))
                .forEach(replyReact -> replyReact.setNotificationStatus(NotificationStatus.READ));
        replyReactRepository.saveAll(unreadReactions);
        log.debug("Reading all reply reaction for current user with id of {} success", currentUser.getId());
    }
}

package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.repository.react.ReplyReactRepository;
import com.elleined.forumapi.service.notification.react.ReactNotificationService;
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
public class ReplyReactNotificationReader implements ReactNotificationReader {
    private final ReactNotificationService<ReplyReact> replyReactNotificationService;
    private final ReplyReactRepository replyReactRepository;

    @Override
    public void readAll(User currentUser) {
        List<ReplyReact> unreadReactions = replyReactNotificationService.getAllUnreadNotification(currentUser);
        unreadReactions.forEach(replyReact -> replyReact.setNotificationStatus(NotificationStatus.READ));
        replyReactRepository.saveAll(unreadReactions);
        log.debug("Reading all reply reaction for current user with id of {} success", currentUser.getId());
    }
}

package com.elleined.forumapi.service.notification.react.reader;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.Emoji;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.repository.react.PostReactRepository;
import com.elleined.forumapi.service.notification.react.ReactNotificationService;
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
public class PostReactNotificationReader implements ReactNotificationReader {
    private final ReactNotificationService<PostReact> postReactNotificationService;
    private final PostReactRepository postReactRepository;

    @Override
    public void readAll(User currentUser) {
        List<PostReact> unreadReactions = postReactNotificationService.getAllUnreadNotification(currentUser);
        unreadReactions.forEach(postReact -> postReact.setNotificationStatus(NotificationStatus.READ));
        postReactRepository.saveAll(unreadReactions);
        log.debug("Reading all post reaction for current user with id of {} success", currentUser.getId());
    }

    @Override
    public void readAll(User currentUser, Emoji.Type type) {
        List<PostReact> unreadReactions = postReactNotificationService.getAllUnreadNotification(currentUser).stream()
                .filter(postReact -> postReact.getEmoji().getType() == type)
                .toList();

        unreadReactions.forEach(postReact -> postReact.setNotificationStatus(NotificationStatus.READ));
        postReactRepository.saveAll(unreadReactions);
        log.debug("Reading all post reaction for current user with id of {} success", currentUser.getId());
    }
}

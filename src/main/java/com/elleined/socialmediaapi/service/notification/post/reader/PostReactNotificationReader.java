package com.elleined.socialmediaapi.service.notification.post.reader;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.react.PostReact;
import com.elleined.socialmediaapi.repository.react.PostReactRepository;
import com.elleined.socialmediaapi.service.notification.react.PostReactNotificationService;
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
public class PostReactNotificationReader implements PostNotificationReader {
    private final PostReactNotificationService postReactNotificationService;
    private final PostReactRepository postReactRepository;

    @Override
    public void readAll(User currentUser) {
        List<PostReact> unreadReactions = postReactNotificationService.getAllUnreadNotification(currentUser);
        unreadReactions.forEach(postReact -> postReact.setNotificationStatus(NotificationStatus.READ));
        postReactRepository.saveAll(unreadReactions);
        log.debug("Reading all post reaction for current user with id of {} success", currentUser.getId());
    }
}

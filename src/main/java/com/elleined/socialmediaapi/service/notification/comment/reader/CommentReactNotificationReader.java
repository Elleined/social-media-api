package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.react.CommentReact;
import com.elleined.socialmediaapi.repository.react.CommentReactRepository;
import com.elleined.socialmediaapi.service.notification.react.CommentReactNotificationService;
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
@Qualifier("commentReactNotificationReader")
public class CommentReactNotificationReader implements CommentNotificationReaderService {
    private final CommentReactNotificationService commentReactNotificationService;
    private final CommentReactRepository commentReactRepository;
    @Override
    public void readAll(User currentUser, Post post) {
        List<CommentReact> unreadReactions = commentReactNotificationService.getAllUnreadNotification(currentUser);
        unreadReactions.stream()
                .filter(commentReact -> commentReact.getComment().getPost().equals(post))
                .forEach(commentReact -> commentReact.setNotificationStatus(NotificationStatus.READ));
        commentReactRepository.saveAll(unreadReactions);
        log.debug("Reading all comment reaction for current user with id of {} success", currentUser.getId());
    }
}

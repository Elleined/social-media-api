package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.service.notification.comment.CommentNotificationService;
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
public class CommentNotificationReader implements CommentNotificationReaderService {
    private final CommentNotificationService commentNotificationService;
    private final CommentRepository commentRepository;

    @Override
    public void readAll(User currentUser, Post post) {
        List<Comment> unreadComments = commentNotificationService.getAllUnreadNotification(currentUser);
        unreadComments.stream()
                .filter(comment -> comment.getPost().equals(post))
                .forEach(comment -> comment.setNotificationStatus(NotificationStatus.READ));
        commentRepository.saveAll(unreadComments);
        log.debug("Comments in post with id of {} read successfully!", post.getId());
    }
}

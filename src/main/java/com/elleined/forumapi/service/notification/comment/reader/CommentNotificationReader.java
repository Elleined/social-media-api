package com.elleined.forumapi.service.notification.comment.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.service.notification.comment.CommentNotificationService;
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

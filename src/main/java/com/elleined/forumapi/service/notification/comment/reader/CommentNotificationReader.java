package com.elleined.forumapi.service.notification.comment.reader;

import com.elleined.forumapi.model.*;
import com.elleined.forumapi.repository.CommentRepository;
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
public class CommentNotificationReader implements CommentNotificationReaderService {
    private final BlockService blockService;
    private final CommentRepository commentRepository;

    @Override
    public void readAll(User currentUser, Post post) {
        if (!currentUser.equals(post.getAuthor())) {
            log.trace("Will not mark as unread because the current user with id of {} are not the author of the post who is {}", currentUser.getId(), post.getAuthor().getId());
            return;
        }
        log.trace("Will mark all as read becuase the current user with id of {} is the author of the post {}", currentUser.getId(), post.getAuthor().getId());
        List<Comment> comments = post.getComments()
                .stream()
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
                .toList();

        comments.forEach(comment -> comment.setNotificationStatus(NotificationStatus.READ));
        commentRepository.saveAll(comments);
        log.debug("Comments in post with id of {} read successfully!", post.getId());
    }
}

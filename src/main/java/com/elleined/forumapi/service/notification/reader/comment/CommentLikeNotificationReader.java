package com.elleined.forumapi.service.notification.reader.comment;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.notification.like.LikeNotificationService;
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
@Qualifier("commentLikeNotificationReader")
public class CommentLikeNotificationReader implements CommentNotificationReaderService {
    private final LikeNotificationService<CommentLike> commentLikeNotificationService;
    private final LikeRepository likeRepository;

    @Override
    public void readAll(User currentUser, Post post) {
        List<CommentLike> commentLikes = commentLikeNotificationService.getAllUnreadNotification(currentUser);
        commentLikes.stream()
                .filter(commentLike -> commentLike.getComment().getPost().equals(post))
                .forEach(commentLike -> commentLike.setNotificationStatus(NotificationStatus.READ));
        likeRepository.saveAll(commentLikes);
        log.debug("Reading all unread comment like for current user with id of {} success", currentUser.getId());
    }
}

package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.like.notification.CommentLikeNotificationService;
import com.elleined.forumapi.service.like.notification.PostLikeNotificationService;
import com.elleined.forumapi.service.like.notification.ReplyLikeNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class LikeNotificationReaderService {
    private final PostLikeNotificationService postLikeNotificationService;
    private final CommentLikeNotificationService commentLikeNotificationService;
    private final ReplyLikeNotificationService replyLikeNotificationService;
    private final LikeRepository likeRepository;

    void readLikes(User currentUser) {
        List<PostLike> postLikes = postLikeNotificationService.getAllUnreadLikeNotification(currentUser);
        postLikes.forEach(this::readLikeNotification);
        likeRepository.saveAll(postLikes);
        log.debug("Reading all unread post like for current user with id of {} success", currentUser.getId());
    }

    void readLikes(User currentUser, Post post) {
        List<CommentLike> commentLikes = commentLikeNotificationService.getAllUnreadLikeNotification(currentUser);
        commentLikes.stream()
                .filter(like -> like.getComment().getPost().equals(post))
                .forEach(this::readLikeNotification);
        likeRepository.saveAll(commentLikes);
        log.debug("Reading all unread comment like for current user with id of {} success", currentUser.getId());
    }

    void readLikes(User currentUser, Comment comment) {
        List<ReplyLike> replyLikes = replyLikeNotificationService.getAllUnreadLikeNotification(currentUser);
        replyLikes.stream()
                .filter(like -> like.getReply().getComment().equals(comment))
                .forEach(this::readLikeNotification);
        likeRepository.saveAll(replyLikes);
        log.debug("Reading all unread reply like for current user with id of {} success", currentUser.getId());
    }

    private void readLikeNotification(Like like) {
        like.setNotificationStatus(NotificationStatus.READ);
    }
}
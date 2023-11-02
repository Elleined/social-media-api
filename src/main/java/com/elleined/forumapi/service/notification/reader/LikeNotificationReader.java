package com.elleined.forumapi.service.notification.reader;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.like.CommentLikeService;
import com.elleined.forumapi.service.like.PostLikeService;
import com.elleined.forumapi.service.like.ReplyLikeService;
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
@Qualifier("likeNotificationReaderService")
class LikeNotificationReader implements NotificationReader {
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;
    private final ReplyLikeService replyLikeService;
    private final LikeRepository likeRepository;

    private void readNotification(Like like) {
        like.setNotificationStatus(NotificationStatus.READ);
    }

    @Override
    public void read(User currentUser) {
        List<PostLike> postLikes = postLikeService.getAllUnreadNotification(currentUser);
        postLikes.forEach(this::readNotification);
        likeRepository.saveAll(postLikes);
        log.debug("Reading all unread post like for current user with id of {} success", currentUser.getId());
    }

    @Override
    public void read(User currentUser, Post post) {
        List<CommentLike> commentLikes = commentLikeService.getAllUnreadNotification(currentUser);
        commentLikes.stream()
                .filter(like -> like.getComment().getPost().equals(post))
                .forEach(this::readNotification);
        likeRepository.saveAll(commentLikes);
        log.debug("Reading all unread comment like for current user with id of {} success", currentUser.getId());
    }

    @Override
    public void read(User currentUser, Comment comment) {
        List<ReplyLike> replyLikes = replyLikeService.getAllUnreadNotification(currentUser);
        replyLikes.stream()
                .filter(like -> like.getReply().getComment().equals(comment))
                .forEach(this::readNotification);
        likeRepository.saveAll(replyLikes);
        log.debug("Reading all unread reply like for current user with id of {} success", currentUser.getId());
    }
}
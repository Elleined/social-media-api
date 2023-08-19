package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final ModalTrackerService modalTrackerService;
    private final LikeNotificationService likeNotificationService;
    private final LikeNotificationReaderService likeNotificationReaderService;

    PostLike like(User respondent, Post post) {
        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(post.getAuthor().getId(), post.getId(), ModalTracker.Type.POST)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        PostLike postLike = PostLike.postLikeBuilder()
                .respondent(respondent)
                .post(post)
                .notificationStatus(notificationStatus)
                .createdAt(LocalDateTime.now())
                .build();

        respondent.getLikedPosts().add(postLike);
        post.getLikes().add(postLike);
        likeRepository.save(postLike);
        log.debug("User with id of {} liked post with id of {}", respondent.getId(), post.getId());
        return postLike;
    }

    public boolean isUserAlreadyLiked(User respondent, Post post) {
        return respondent.getLikedPosts().stream()
                .map(PostLike::getPost)
                .anyMatch(post::equals);
    }

    public boolean isUserAlreadyLiked(User respondent, PostDTO postDTO) {
        return respondent.getLikedPosts().stream()
                .map(PostLike::getPost)
                .map(Post::getId)
                .anyMatch(postId -> postId == postDTO.getId());
    }

    void unlike(User respondent, Post post) {
        PostLike postLike = respondent.getLikedPosts()
                .stream()
                .filter(like -> like.getPost().equals(post))
                .findFirst()
                .orElseThrow();

        respondent.getLikedPosts().remove(postLike);
        post.getLikes().remove(postLike);
        likeRepository.delete(postLike);
        log.debug("User with id of {} unlike post with id of {}", respondent.getId(), post.getId());
    }
    CommentLike like(User respondent, Comment comment) {
        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(comment.getCommenter().getId(), comment.getPost().getId(), ModalTracker.Type.COMMENT)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        CommentLike commentLike = CommentLike.commentLikeBuilder()
                .respondent(respondent)
                .comment(comment)
                .notificationStatus(notificationStatus)
                .createdAt(LocalDateTime.now())
                .build();

        respondent.getLikedComments().add(commentLike);
        comment.getLikes().add(commentLike);
        likeRepository.save(commentLike);
        log.debug("User with id of {} liked comment with id of {}", respondent.getId(), comment.getId());
        return commentLike;
    }

    public boolean isUserAlreadyLiked(User respondent, Comment comment) {
        return respondent.getLikedComments().stream()
                .map(CommentLike::getComment)
                .anyMatch(comment::equals);
    }

    public boolean isUserAlreadyLiked(User respondent, CommentDTO commentDTO) {
        return respondent.getLikedComments().stream()
                .map(CommentLike::getComment)
                .map(Comment::getId)
                .anyMatch(commentId -> commentId == commentDTO.getId());
    }


    void unlike(User respondent, Comment comment) {
        CommentLike commentLike = respondent.getLikedComments().stream()
                .filter(likedComment -> likedComment.getComment().equals(comment))
                .findFirst()
                .orElseThrow();

        respondent.getLikedComments().remove(commentLike);
        comment.getLikes().remove(commentLike);
        likeRepository.delete(commentLike);
        log.debug("User with id of {} unlike comment with id of {}", respondent.getId(), comment.getId());
    }

    ReplyLike like(User respondent, Reply reply) {
        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(reply.getReplier().getId(), reply.getComment().getId(), ModalTracker.Type.REPLY)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        ReplyLike replyLike = ReplyLike.replyLikeBuilder()
                .respondent(respondent)
                .reply(reply)
                .notificationStatus(notificationStatus)
                .createdAt(LocalDateTime.now())
                .build();

        respondent.getLikedReplies().add(replyLike);
        reply.getLikes().add(replyLike);
        likeRepository.save(replyLike);
        log.debug("User with id of {} liked reply with id of {}", respondent.getId(), reply.getId());
        return replyLike;
    }

    public boolean isUserAlreadyLiked(User respondent, Reply reply) {
        return respondent.getLikedReplies().stream()
                .map(ReplyLike::getReply)
                .anyMatch(reply::equals);
    }

    public boolean isUserAlreadyLiked(User respondent, ReplyDTO replyDTO) {
        return respondent.getLikedReplies().stream()
                .map(ReplyLike::getReply)
                .map(Reply::getId)
                .anyMatch(replyId -> replyId == replyDTO.getId());
    }


    void unlike(User respondent, Reply reply) {
        ReplyLike replyLike = respondent.getLikedReplies()
                .stream()
                .filter(likedReply -> likedReply.getReply().equals(reply))
                .findFirst()
                .orElseThrow();

        respondent.getLikedReplies().remove(replyLike);
        reply.getLikes().remove(replyLike);
        likeRepository.delete(replyLike);
        log.debug("User with id of {} unliked reply with id of {}", respondent.getId(), reply.getId());
    }

    Set<PostLike> getUnreadPostLikes(User currentUser) {
        return likeNotificationService.getUnreadPostLikes(currentUser);
    }

    Set<CommentLike> getUnreadCommentLikes(User currentUser) {
        return likeNotificationService.getUnreadCommentLikes(currentUser);
    }

    Set<ReplyLike> getUnreadReplyLikes(User currentUser) {
        return likeNotificationService.getUnreadReplyLikes(currentUser);
    }


    void readLikes(User currentUser) {
        likeNotificationReaderService.readLikes(currentUser);
    }
    void readLikes(User currentUser, Post post) {
        likeNotificationReaderService.readLikes(currentUser, post);
    }
    void readLikes(User currentUser, Comment comment) {
        likeNotificationReaderService.readLikes(currentUser, comment);
    }

    @Service
    @RequiredArgsConstructor
    private static class LikeNotificationService {
        private final BlockService blockService;

        private Set<PostLike> getUnreadPostLikes(User currentUser) {
            return currentUser.getPosts()
                    .stream()
                    .map(Post::getLikes)
                    .flatMap(likes -> likes.stream()
                            .filter(like -> like.getPost().getStatus() == Status.ACTIVE)
                            .filter(like -> like.getNotificationStatus() == NotificationStatus.UNREAD)
                            .filter(like -> !blockService.isBlockedBy(currentUser, like.getRespondent()))
                            .filter(like -> !blockService.isYouBeenBlockedBy(currentUser, like.getRespondent())))
                    .collect(Collectors.toSet());
        }

        private Set<CommentLike> getUnreadCommentLikes(User currentUser) {
            return currentUser.getComments()
                    .stream()
                    .map(Comment::getLikes)
                    .flatMap(likes -> likes.stream()
                            .filter(like -> like.getComment().getStatus() == Status.ACTIVE)
                            .filter(like -> like.getNotificationStatus() == NotificationStatus.UNREAD)
                            .filter(like -> !blockService.isBlockedBy(currentUser, like.getRespondent()))
                            .filter(like -> !blockService.isYouBeenBlockedBy(currentUser, like.getRespondent())))
                    .collect(Collectors.toSet());
        }

        private Set<ReplyLike> getUnreadReplyLikes(User currentUser) {
            return currentUser.getReplies()
                    .stream()
                    .map(Reply::getLikes)
                    .flatMap(likes -> likes.stream()
                            .filter(like -> like.getReply().getStatus() == Status.ACTIVE)
                            .filter(like -> like.getNotificationStatus() == NotificationStatus.UNREAD)
                            .filter(like -> !blockService.isBlockedBy(currentUser, like.getRespondent()))
                            .filter(like -> !blockService.isYouBeenBlockedBy(currentUser, like.getRespondent())))
                    .collect(Collectors.toSet());
        }
    }

    @Service
    @RequiredArgsConstructor
    private static class LikeNotificationReaderService {
        private final LikeNotificationService likeNotificationService;
        private final LikeRepository likeRepository;

        private void readLikes(User currentUser) {
            Set<PostLike> postLikes = likeNotificationService.getUnreadPostLikes(currentUser);
            postLikes.forEach(this::readLikeNotification);
            likeRepository.saveAll(postLikes);
            log.debug("Reading all unread post like for current user with id of {} success", currentUser.getId());
        }

        private void readLikes(User currentUser, Post post) {
            Set<CommentLike> commentLikes = likeNotificationService.getUnreadCommentLikes(currentUser);
            commentLikes.stream()
                    .filter(like -> like.getComment().getPost().equals(post))
                    .forEach(this::readLikeNotification);
            likeRepository.saveAll(commentLikes);
            log.debug("Reading all unread comment like for current user with id of {} success", currentUser.getId());
        }

        private void readLikes(User currentUser, Comment comment) {
            Set<ReplyLike> replyLikes = likeNotificationService.getUnreadReplyLikes(currentUser);
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
}

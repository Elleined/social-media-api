package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService implements LikeService<Comment> {
    private final ModalTrackerService modalTrackerService;
    private final LikeRepository likeRepository;

    @Override
    public CommentLike like(User respondent, Comment comment) {
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

    @Override
    public void unLike(User respondent, Comment comment) {
        CommentLike commentLike = respondent.getLikedComments().stream()
                .filter(likedComment -> likedComment.getComment().equals(comment))
                .findFirst()
                .orElseThrow();

        respondent.getLikedComments().remove(commentLike);
        comment.getLikes().remove(commentLike);
        likeRepository.delete(commentLike);
        log.debug("User with id of {} unlike comment with id of {}", respondent.getId(), comment.getId());
    }

    @Override
    public boolean isLiked(User respondent, Comment comment) {
        return respondent.getLikedComments().stream()
                .map(CommentLike::getComment)
                .anyMatch(comment::equals);
    }
}
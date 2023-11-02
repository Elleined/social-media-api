package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.Like;
import com.elleined.forumapi.model.like.ReplyLike;
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
public class ReplyLikeService implements LikeService<Reply> {
    private final ModalTrackerService modalTrackerService;
    private final LikeRepository likeRepository;


    @Override
    public ReplyLike like(User respondent, Reply reply) {
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

    @Override
    public void unLike(User respondent, Reply reply) {
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

    @Override
    public boolean isLiked(User respondent, Reply reply) {
        return respondent.getLikedReplies().stream()
                .map(ReplyLike::getReply)
                .anyMatch(reply::equals);
    }
}

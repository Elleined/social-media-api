package com.elleined.forumapi.service.notification.reader.reply;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.ReplyLike;
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
@Qualifier("replyLikeNotificationReader")
public class ReplyLikeNotificationReader implements ReplyNotificationReaderService {
    private final LikeNotificationService<ReplyLike> replyLikeNotificationService;
    private final LikeRepository likeRepository;

    @Override
    public void readAll(User currentUser, Comment comment) {
        List<ReplyLike> replyLikes = replyLikeNotificationService.getAllUnreadNotification(currentUser);
        replyLikes.stream()
                .filter(replyLike -> replyLike.getReply().getComment().equals(comment))
                .forEach(replyLike -> replyLike.setNotificationStatus(NotificationStatus.READ));
        likeRepository.saveAll(replyLikes);
        log.debug("Reading all unread reply like for current user with id of {} success", currentUser.getId());
    }
}

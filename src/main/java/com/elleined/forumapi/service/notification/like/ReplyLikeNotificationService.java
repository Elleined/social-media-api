package com.elleined.forumapi.service.notification.like;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.BlockService;
import com.elleined.forumapi.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyLikeNotificationService implements LikeNotificationService<ReplyLike> {
    private final BlockService blockService;

    @Override
    public List<ReplyLike> getAllNotification(User currentUser) {
        return currentUser.getReplies()
                .stream()
                .map(Reply::getLikes)
                .flatMap(likes -> likes.stream()
                        .filter(like -> like.getReply().getStatus() == Status.ACTIVE)
                        .filter(like -> like.getNotificationStatus() == NotificationStatus.UNREAD)
                        .filter(like -> !blockService.isBlockedBy(currentUser, like.getRespondent()))
                        .filter(like -> !blockService.isYouBeenBlockedBy(currentUser, like.getRespondent())))
                .toList();
    }
}

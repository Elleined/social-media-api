package com.elleined.forumapi.service.notification.like;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeNotificationService implements LikeNotificationService<PostLike> {
    private final BlockService blockService;
    @Override
    public List<PostLike> getAllUnreadNotification(User currentUser) {
        return currentUser.getPosts()
                .stream()
                .map(Post::getLikes)
                .flatMap(likes -> likes.stream()
                        .filter(like -> like.getPost().getStatus() == Status.ACTIVE)
                        .filter(like -> like.getNotificationStatus() == NotificationStatus.UNREAD)
                        .filter(like -> !blockService.isBlockedBy(currentUser, like.getRespondent()))
                        .filter(like -> !blockService.isYouBeenBlockedBy(currentUser, like.getRespondent())))
                .toList();
    }

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}

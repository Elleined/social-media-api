package com.elleined.forumapi.service.notification.reader.post;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.PostLike;
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
@Qualifier("postLikeNotificationReader")
public class PostLikeNotificationReader implements PostNotificationReaderService {

    private final LikeNotificationService<PostLike> postLikeNotificationService;
    private final LikeRepository likeRepository;

    @Override
    public void readAll(User currentUser) {
        List<PostLike> postLikes = postLikeNotificationService.getAllUnreadNotification(currentUser);
        postLikes.forEach(postLike -> postLike.setNotificationStatus(NotificationStatus.READ));
        likeRepository.saveAll(postLikes);
        log.debug("Reading all unread post like for current user with id of {} success", currentUser.getId());
    }
}

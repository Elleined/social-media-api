package com.elleined.forumapi.service.like;

import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.BlockService;
import com.elleined.forumapi.service.ModalTrackerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService implements LikeService<PostLike, Post> {
    private final ModalTrackerService modalTrackerService;
    private final LikeRepository likeRepository;
    private final BlockService blockService;

    @Override
    public PostLike like(User respondent, Post post) {
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

    @Override
    public void unLike(User respondent, Post post) {
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

    @Override
    public boolean isLiked(User respondent, Post post) {
        return respondent.getLikedPosts().stream()
                .map(PostLike::getPost)
                .anyMatch(post::equals);
    }

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
}

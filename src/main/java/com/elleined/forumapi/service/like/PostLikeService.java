package com.elleined.forumapi.service.like;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.repository.LikeRepository;
import com.elleined.forumapi.service.BlockService;
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
public class PostLikeService implements LikeService<Post> {
    private final BlockService blockService;
    private final ModalTrackerService modalTrackerService;
    private final LikeRepository likeRepository;

    @Override
    public PostLike like(User respondent, Post post)
            throws ResourceNotFoundException,
            BlockedException {

        if (post.isDeleted()) throw new ResourceNotFoundException("Cannot like/unlike! The post with id of " + post.getId() + " you are trying to like/unlike might already been deleted or does not exists!");
        if (blockService.isBlockedBy(respondent, post.getAuthor())) throw new BlockedException("Cannot like/unlike! You blocked the author of this post with id of !" + post.getAuthor().getId());
        if (blockService.isYouBeenBlockedBy(respondent, post.getAuthor())) throw  new BlockedException("Cannot like/unlike! The author of this post with id of " + post.getAuthor().getId() + " already blocked you");

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
}

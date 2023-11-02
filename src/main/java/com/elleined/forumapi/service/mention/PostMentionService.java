package com.elleined.forumapi.service.mention;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.MentionException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.BlockService;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostMentionService implements MentionService<PostMention, Post> {
    private final PostService postService;

    private final MentionRepository mentionRepository;

    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    @Override
    public PostMention mention(User mentioningUser, User mentionedUser, Post post) {
        if (postService.isDeleted(post)) throw new ResourceNotFoundException("Cannot mention! The post with id of " + post.getId() + " you are trying to mention might already been deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw  new BlockedException("Cannot mention! Mentioned user with id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");

        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), post.getId(), ModalTracker.Type.POST)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        PostMention postMention = PostMention.postMentionBuilder()
                .mentioningUser(mentioningUser)
                .mentionedUser(mentionedUser)
                .createdAt(LocalDateTime.now())
                .notificationStatus(notificationStatus)
                .post(post)
                .build();

        mentioningUser.getSentPostMentions().add(postMention);
        mentionedUser.getReceivePostMentions().add(postMention);
        post.getMentions().add(postMention);
        mentionRepository.save(postMention);
        log.debug("User with id of {} mentioned user with id of {} in post with id of {}", mentioningUser.getId(), mentionedUser.getId(), post.getId());
        return postMention;
    }

    @Override
    public List<PostMention> mentionAll(User mentioningUser, Set<User> mentionedUsers, Post post) {
        return mentionedUsers.stream()
                .map(mentionedUser -> mention(mentioningUser, mentionedUser, post))
                .toList();
    }
}

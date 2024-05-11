package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.MentionException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.mention.PostMentionMapper;
import com.elleined.socialmediaapi.model.ModalTracker;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.service.ModalTrackerService;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostMentionService implements MentionService<Post> {

    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    private final PostMentionMapper postMentionMapper;
    private final MentionRepository mentionRepository;

    @Override
    public PostMention mention(User mentioningUser, User mentionedUser, Post post) {
        if (post.isInactive()) throw new ResourceNotFoundException("Cannot mention! The post with id of " + post.getId() + " you are trying to mention might already been deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw  new BlockedException("Cannot mention! Mentioned user with id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");

        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), post.getId(), ModalTracker.Type.POST) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        PostMention postMention = postMentionMapper.toEntity(mentioningUser, mentionedUser, post, notificationStatus);

        mentionRepository.save(postMention);
        log.debug("User with id of {} mentioned user with id of {} in post with id of {}", mentioningUser.getId(), mentionedUser.getId(), post.getId());
        return postMention;
    }

    @Override
    public void mentionAll(User mentioningUser, Set<User> mentionedUsers, Post post) {
        mentionedUsers.forEach(mentionedUser -> mention(mentioningUser, mentionedUser, post));
    }
}

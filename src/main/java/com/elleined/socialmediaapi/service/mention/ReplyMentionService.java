package com.elleined.socialmediaapi.service.mention;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.MentionException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.mention.ReplyMentionMapper;
import com.elleined.socialmediaapi.model.ModalTracker;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
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
public class ReplyMentionService implements MentionService<Reply> {

    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    private final MentionRepository mentionRepository;
    private final ReplyMentionMapper replyMentionMapper;

    @Override
    public ReplyMention mention(User mentioningUser, User mentionedUser, Reply reply) {
        if (reply.isInactive()) throw new ResourceNotFoundException("Cannot mention! The reply with id of " + reply.getId() + " you are trying to mention might already be deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! Mentioned userwith id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");

        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), reply.getComment().getId(), ModalTracker.Type.REPLY) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        ReplyMention replyMention = replyMentionMapper.toEntity(mentioningUser, mentionedUser, reply, notificationStatus);

        mentionRepository.save(replyMention);
        log.debug("User with id of {} mentioned user with id of {} in reply with id of {}", mentioningUser.getId(), mentionedUser.getId(), reply.getId());
        return replyMention;
    }

    @Override
    public void mentionAll(User mentioningUser, Set<User> mentionedUsers, Reply reply) {
        mentionedUsers.forEach(mentionedUser -> mention(mentioningUser, mentionedUser, reply));
    }
}

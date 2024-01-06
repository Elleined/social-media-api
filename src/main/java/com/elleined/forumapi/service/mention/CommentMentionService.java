package com.elleined.forumapi.service.mention;

import com.elleined.forumapi.exception.BlockedException;
import com.elleined.forumapi.exception.MentionException;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.ModalTrackerService;
import com.elleined.forumapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentMentionService implements MentionService<Comment> {

    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    private final MentionRepository mentionRepository;

    @Override
    public CommentMention mention(User mentioningUser, User mentionedUser, Comment comment) {
        if (comment.isInactive()) throw new ResourceNotFoundException("Cannot mention! The comment with id of " + comment.getId() + " you are trying to mention might already been deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw  new BlockedException("Cannot mention! Mentioned user with id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");

        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), comment.getPost().getId(), ModalTracker.Type.COMMENT)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        CommentMention commentMention = CommentMention.commentMentionBuilder()
                .mentioningUser(mentioningUser)
                .mentionedUser(mentionedUser)
                .createdAt(LocalDateTime.now())
                .comment(comment)
                .notificationStatus(notificationStatus)
                .build();

        mentioningUser.getSentCommentMentions().add(commentMention);
        mentionedUser.getReceiveCommentMentions().add(commentMention);
        comment.getMentions().add(commentMention);
        mentionRepository.save(commentMention);
        log.debug("User with id of {} mentioned user with id of {} in comment with id of {}", mentioningUser.getId(), mentionedUser.getId(), comment.getId());
        return commentMention;
    }

    @Override
    public void mentionAll(User mentioningUser, Set<User> mentionedUsers, Comment comment) {
        mentionedUsers.forEach(mentionedUser -> mention(mentioningUser, mentionedUser, comment));
    }
}

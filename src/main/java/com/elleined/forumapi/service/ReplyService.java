package com.elleined.forumapi.service;

import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.service.block.BlockService;
import com.elleined.forumapi.service.image.ImageUploader;
import com.elleined.forumapi.service.mention.MentionService;
import com.elleined.forumapi.service.pin.PinService;
import com.elleined.forumapi.utils.DirectoryFolders;
import com.elleined.forumapi.validator.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService
        implements MentionService<Reply> {

    private final ReplyRepository replyRepository;

    private final ModalTrackerService modalTrackerService;

    private final BlockService blockService;

    private final ImageUploader imageUploader;

    private final PinService<Comment, Reply> replyPinService;

    private final MentionRepository mentionRepository;

    @Value("${cropTrade.img.directory}")
    private String cropTradeImgDirectory;

    public Reply save(User currentUser, Comment comment, String body, MultipartFile attachedPicture, Set<User> mentionedUsers) throws EmptyBodyException,
            ClosedCommentSectionException,
            ResourceNotFoundException,
            BlockedException, IOException {

        if (StringValidator.isNotValidBody(body)) throw new EmptyBodyException("Reply body cannot be empty!");
        if (comment.isCommentSectionClosed()) throw new ClosedCommentSectionException("Cannot reply to this comment because author already closed the comment section for this post!");
        if (comment.isDeleted()) throw new ResourceNotFoundException("The comment you trying to reply is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because this user block you already!");

        NotificationStatus status = modalTrackerService.isModalOpen(comment.getCommenter().getId(), comment.getId(), ModalTracker.Type.REPLY) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        Reply reply = Reply.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .replier(currentUser)
                .comment(comment)
                .attachedPicture(attachedPicture == null ? null : attachedPicture.getOriginalFilename())
                .status(Status.ACTIVE)
                .notificationStatus(status)
                .mentions(new HashSet<>())
                .build();

        currentUser.getReplies().add(reply);
        comment.getReplies().add(reply);
        replyRepository.save(reply);

        if (attachedPicture != null) imageUploader.upload(cropTradeImgDirectory + DirectoryFolders.REPLY_PICTURE_FOLDER, attachedPicture);

        if (mentionedUsers != null) mentionAll(currentUser, mentionedUsers, reply);
        log.debug("Reply with id of {} saved successfully!", reply.getId());
        return reply;
    }

    public void delete(User currentUser, Comment comment, Reply reply) throws NotOwnedException {
        if (comment.doesNotHave(reply)) throw new NotOwnedException("Comment with id of " + comment.getId() +  " does not have reply with id of " + reply.getId());
        if (currentUser.notOwned(reply)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        reply.setStatus(Status.INACTIVE);
        replyRepository.save(reply);
        if (comment.getPinnedReply() != null && comment.getPinnedReply().equals(reply)) replyPinService.unpin(reply);
        log.debug("Reply with id of {} are now inactive!", reply.getId());
    }

    public Reply updateBody(User currentUser, Reply reply, String newReplyBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        if (reply.getBody().equals(newReplyBody)) return reply;
        if (currentUser.notOwned(reply)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        reply.setBody(newReplyBody);
        replyRepository.save(reply);
        log.debug("Reply with id of {} updated with the new body of {}", reply.getId(), newReplyBody);
        return reply;
    }

    public List<Reply> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException {
        if (comment.isDeleted()) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists anymore!");

        Reply pinnedReply = comment.getPinnedReply();
        List<Reply> replies = new ArrayList<>(comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> !reply.equals(pinnedReply))
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .sorted(Comparator.comparing(Reply::getDateCreated))
                .toList());
        if (pinnedReply != null) replies.add(0, pinnedReply); // Prioritizing pinned reply
        return replies;
    }

    public Reply getById(int replyId) throws ResourceNotFoundException {
        return replyRepository.findById(replyId).orElseThrow(() -> new ResourceNotFoundException("Reply with id of " + replyId + " does not exists!"));
    }

    @Override
    public ReplyMention mention(User mentioningUser, User mentionedUser, Reply reply) {
        if (reply.isDeleted()) throw new ResourceNotFoundException("Cannot mention! The reply with id of " + reply.getId() + " you are trying to mention might already be deleted or does not exists!");
        if (blockService.isBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! You blocked the mentioned user with id of !" + mentionedUser.getId());
        if (blockService.isYouBeenBlockedBy(mentioningUser, mentionedUser)) throw new BlockedException("Cannot mention! Mentioned userwith id of " + mentionedUser.getId() + " already blocked you");
        if (mentioningUser.equals(mentionedUser)) throw new MentionException("Cannot mention! You are trying to mention yourself which is not possible!");


        NotificationStatus notificationStatus = modalTrackerService.isModalOpen(mentionedUser.getId(), reply.getComment().getId(), ModalTracker.Type.REPLY)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        ReplyMention replyMention = ReplyMention.replyMentionBuilder()
                .mentioningUser(mentioningUser)
                .mentionedUser(mentionedUser)
                .createdAt(LocalDateTime.now())
                .reply(reply)
                .notificationStatus(notificationStatus)
                .build();

        mentioningUser.getSentReplyMentions().add(replyMention);
        mentionedUser.getReceiveReplyMentions().add(replyMention);
        reply.getMentions().add(replyMention);
        mentionRepository.save(replyMention);
        log.debug("User with id of {} mentioned user with id of {} in reply with id of {}", mentioningUser.getId(), mentionedUser.getId(), reply.getId());
        return replyMention;
    }

    @Override
    public void mentionAll(User mentioningUser, Set<User> mentionedUsers, Reply reply) {
        mentionedUsers.stream()
                .map(mentionedUser -> mention(mentioningUser, mentionedUser, reply))
                .toList();
    }
}

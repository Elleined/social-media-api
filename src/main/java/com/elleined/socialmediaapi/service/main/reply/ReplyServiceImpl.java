package com.elleined.socialmediaapi.service.main.reply;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.mapper.ReplyMapper;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.entity.ReplyHashTagService;
import com.elleined.socialmediaapi.service.mention.ReplyMentionService;
import com.elleined.socialmediaapi.service.mt.ModalTrackerService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    private final ModalTrackerService modalTrackerService;

    private final BlockService blockService;

    private final CommentPinReplyService commentPinReplyService;

    private final ReplyMentionService replyMentionService;

    private final ReplyHashTagService replyHashTagService;

    private final Validator validator;

    @Override
    public Reply save(User currentUser,
                      Comment comment,
                      String body,
                      MultipartFile attachedPicture,
                      Set<User> mentionedUsers,
                      Set<String> keywords) throws EmptyBodyException,
            ClosedCommentSectionException,
            ResourceNotFoundException,
            BlockedException, IOException {

        if (validator.isNotValid(body)) throw new EmptyBodyException("Reply body cannot be empty!");
        if (comment.isCommentSectionClosed()) throw new ClosedCommentSectionException("Cannot reply to this comment because author already closed the comment section for this post!");
        if (comment.isInactive()) throw new ResourceNotFoundException("The comment you trying to reply is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())) throw new BlockedException("Cannot reply because this user block you already!");

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        NotificationStatus status = modalTrackerService.isModalOpen(comment.getCommenter().getId(), comment.getId(), ModalTracker.Type.REPLY) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        Reply reply = replyMapper.toEntity(body, currentUser, comment, picture, status);
        replyRepository.save(reply);

        if (validator.isValid(mentionedUsers)) replyMentionService.mentionAll(currentUser, mentionedUsers, reply);
        if (validator.isValid(keywords)) replyHashTagService.saveAll(reply, keywords);
        log.debug("Reply with id of {} saved successfully!", reply.getId());
        return reply;
    }

    @Override
    public void delete(User currentUser, Comment comment, Reply reply) throws NotOwnedException {
        if (comment.doesNotHave(reply)) throw new NotOwnedException("Comment with id of " + comment.getId() +  " does not have reply with id of " + reply.getId());
        if (currentUser.notOwned(reply)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        reply.setStatus(Status.INACTIVE);
        replyRepository.save(reply);
        if (comment.getPinnedReply() != null && comment.getPinnedReply().equals(reply)) commentPinReplyService.unpin(comment);
        log.debug("Reply with id of {} are now inactive!", reply.getId());
    }

    @Override
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

    @Override
    public List<Reply> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException {
        if (comment.isInactive()) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists anymore!");

        Reply pinnedReply = comment.getPinnedReply();
        List<Reply> replies = new ArrayList<>(comment.getReplies().stream()
                .filter(Reply::isActive)
                .filter(reply -> !reply.equals(pinnedReply))
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .sorted(Comparator.comparing(Reply::getDateCreated).reversed())
                .toList());
        if (pinnedReply != null) replies.add(0, pinnedReply); // Prioritizing pinned reply
        return replies;
    }

    @Override
    public Reply getById(int replyId) throws ResourceNotFoundException {
        return replyRepository.findById(replyId).orElseThrow(() -> new ResourceNotFoundException("Reply with id of " + replyId + " does not exists!"));
    }
}

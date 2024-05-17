package com.elleined.socialmediaapi.service.main.reply;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.utility.FieldUtil;
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

    private final BlockService blockService;

    private final CommentPinReplyService commentPinReplyService;

    private final MentionService mentionService;
    private final HashTagService hashTagService;

    @Override
    public Reply save(User currentUser,
                      Comment comment,
                      String body,
                      MultipartFile attachedPicture,
                      Set<User> mentionedUsers,
                      Set<String> keywords) throws EmptyBodyException,
            CommentSectionException,
            ResourceNotFoundException,
            BlockedException, IOException {

        if (FieldUtil.isNotValid(body)) throw new EmptyBodyException("Reply body cannot be empty!");
        if (comment.isCommentSectionClosed()) throw new CommentSectionException("Cannot reply to this comment because author already closed the comment section for this post!");
        if (comment.isInactive()) throw new ResourceNotFoundException("The comment you trying to reply is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, comment.getCreator())) throw new BlockedException("Cannot reply because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator())) throw new BlockedException("Cannot reply because this user block you already!");

        Set<Mention> mentions = mentionService.saveAll(currentUser, mentionedUsers);
        Set<HashTag> hashTags = hashTagService.saveAll(keywords);

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Reply reply = replyMapper.toEntity(currentUser, comment, body, picture, hashTags, mentions);
        replyRepository.save(reply);

        log.debug("Reply with id of {} saved successfully!", reply.getId());
        return reply;
    }

    @Override
    public void delete(User currentUser, Comment comment, Reply reply) throws ResourceNotOwnedException {
        if (comment.hasNot(reply)) throw new ResourceNotOwnedException("Comment with id of " + comment.getId() +  " does not have reply with id of " + reply.getId());
        if (currentUser.notOwned(reply)) throw new ResourceNotOwnedException("User with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        reply.setStatus(Forum.Status.INACTIVE);
        replyRepository.save(reply);
        if (comment.getPinnedReply() != null && comment.getPinnedReply().equals(reply)) commentPinReplyService.unpin(comment);
        log.debug("Reply with id of {} are now inactive!", reply.getId());
    }

    @Override
    public Reply update(User currentUser, Reply reply, String newBody, String newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException {

        if (reply.getBody().equals(newBody)) return reply;
        if (currentUser.notOwned(reply)) throw new ResourceNotOwnedException("User with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        reply.setBody(newBody);
        reply.setAttachedPicture(newAttachedPicture);
        replyRepository.save(reply);
        log.debug("Updating reply success");
        return reply;
    }

    @Override
    public List<Reply> getAllByComment(User currentUser, Comment comment) throws ResourceNotFoundException {
        if (comment.isInactive()) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists anymore!");

        Reply pinnedReply = comment.getPinnedReply();
        List<Reply> replies = new ArrayList<>(comment.getReplies().stream()
                .filter(Reply::isActive)
                .filter(reply -> !reply.equals(pinnedReply))
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getCreator()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getCreator()))
                .sorted(Comparator.comparing(Reply::getCreatedAt).reversed())
                .toList());
        if (pinnedReply != null) replies.add(0, pinnedReply); // Prioritizing pinned reply
        return replies;
    }

    @Override
    public Reply getById(int replyId) throws ResourceNotFoundException {
        return replyRepository.findById(replyId).orElseThrow(() -> new ResourceNotFoundException("Reply with id of " + replyId + " does not exists!"));
    }

    @Override
    public List<Reply> getAllById(Set<Integer> ids) {
        return replyRepository.findAllById(ids);
    }
}

package com.elleined.socialmediaapi.service.main.reply;

import com.elleined.socialmediaapi.exception.CommentSectionException;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.field.FieldException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.main.comment.CommentServiceRestriction;
import com.elleined.socialmediaapi.service.main.post.PostServiceRestriction;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.pin.CommentPinReplyService;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
import com.elleined.socialmediaapi.validator.FieldValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

    private final PostServiceRestriction postServiceRestriction;
    private final CommentServiceRestriction commentServiceRestriction;
    private final UserServiceRestriction userServiceRestriction;

    private final FieldValidator fieldValidator;

    @Override
    public Reply save(User currentUser,
                      Post post, Comment comment,
                      String body,
                      MultipartFile attachedPicture,
                      Set<User> mentionedUsers, Set<HashTag> hashTags) {

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot save reply! because post with id of " + post.getId() + " does not exists or already deleted!") ;

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot save reply! because post doesn't have this comment!");

        if (postServiceRestriction.isCommentSectionClosed(post))
            throw new CommentSectionException("Cannot save reply! because cannot reply to this comment because author already closed the comment section for this post!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot save reply! because the comment you trying to reply is either be deleted or does not exists anymore!");

        if (blockService.isBlockedByYou(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot save reply! because cannot reply because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot save reply! because cannot reply because this user block you already!");

        if (fieldValidator.isNotValid(body))
            throw new FieldException("Cannot save reply! because reply body cannot be empty!");

        Set<Mention> mentions = mentionService.saveAll(currentUser, mentionedUsers);

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Reply reply = replyMapper.toEntity(currentUser, comment, body, picture, mentions, hashTags);
        replyRepository.save(reply);

        log.debug("Reply with id of {} saved successfully!", reply.getId());
        return reply;
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment, Reply reply) throws ResourceNotOwnedException {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot delete reply! because post with id of " + post.getId() + " does not exists or already deleted!") ;

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot delete reply! because post doesn't have this comment!");

        if (postServiceRestriction.isCommentSectionClosed(post))
            throw new CommentSectionException("Cannot delete reply! because cannot reply to this comment because author already closed the comment section for this post!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete reply! because the comment you trying to reply is either be deleted or does not exists anymore!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot delete reply! because comment with id of " + comment.getId() +  " does not have reply with id of " + reply.getId());

        if (userServiceRestriction.notOwned(currentUser, reply))
            throw new ResourceNotOwnedException("Cannot delete reply! because user with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot delete reply! because reply might be already deleted or doesn't exists!");

        if (comment.getPinnedReply() != null && comment.getPinnedReply().equals(reply))
            commentPinReplyService.unpin(comment);

        updateStatus(currentUser, post, comment, reply, Forum.Status.INACTIVE);
        log.debug("Reply with id of {} are now inactive!", reply.getId());
    }

    @Override
    public void update(User currentUser, Post post, Comment comment, Reply reply, String newBody, MultipartFile newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException {

        if (reply.getBody().equals(newBody))
            return reply;

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reply! because post with id of " + post.getId() + " does not exists or already deleted!") ;

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot update reply! because post doesn't have this comment!");

        if (postServiceRestriction.isCommentSectionClosed(post))
            throw new CommentSectionException("Cannot update reply! because cannot reply to this comment because author already closed the comment section for this post!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update reply! because the comment you trying to reply is either be deleted or does not exists anymore!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot update reply! because comment with id of " + comment.getId() +  " does not have reply with id of " + reply.getId());

        if (userServiceRestriction.notOwned(currentUser, reply))
            throw new ResourceNotOwnedException("Cannot update reply! because user with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        if (reply.isInactive())
            throw new ResourceNotFoundException("Cannot update reply! because reply might be already deleted or doesn't exists!");

        String picture = newAttachedPicture == null ? null : newAttachedPicture.getOriginalFilename();

        reply.setBody(newBody);
        reply.setAttachedPicture(picture);
        reply.setUpdatedAt(LocalDateTime.now());

        replyRepository.save(reply);
        log.debug("Updating reply success");
        return reply;
    }

    @Override
    public List<Reply> getAll(User currentUser, Post post, Comment comment) throws ResourceNotFoundException {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update reply! because post with id of " + post.getId() + " does not exists or already deleted!") ;

        if (comment.isInactive())
            throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists anymore!");

        Reply pinnedReply = comment.getPinnedReply();
        List<Reply> replies = new ArrayList<>(comment.getReplies().stream()
                .filter(Reply::isActive)
                .filter(reply -> !reply.equals(pinnedReply))
                .filter(reply -> !blockService.isBlockedByYou(currentUser, reply.getCreator()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getCreator()))
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList());
        if (pinnedReply != null) replies.add(0, pinnedReply); // Prioritizing pinned reply
        return replies;
    }

    @Override
    public void reactivate(User currentUser, Post post, Comment comment, Reply reply) {
        if (userServiceRestriction.notOwned(currentUser, reply))
            throw new ResourceNotOwnedException("Cannot reactivate reply! because user with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot reactivate reply! because post doesn't owned this comment!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot reactivate reply! because comment doesn't owned this reply!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot reactivate reply! because post might be already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot reactivate reply! because comment might be already deleted or doesn't exists!");

        if (reply.isActive())
            throw new ResourceNotFoundException("Cannot reactivate reply! because reply are not deleted or doesn't exists!");

        updateStatus(currentUser, post, comment, reply, Forum.Status.ACTIVE);
        log.debug("Reply with id of {} are now active!", reply.getId());
    }

    @Override
    public void updateStatus(User currentUser, Post post, Comment comment, Reply reply, Forum.Status status) {
        if (userServiceRestriction.notOwned(currentUser, reply))
            throw new ResourceNotOwnedException("Cannot update reply status! because user with id of " + currentUser.getId() + " doesn't have reply with id of " + reply.getId());

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot update reply status! because post doesn't owned this comment!");

        if (commentServiceRestriction.notOwned(comment, reply))
            throw new ResourceNotOwnedException("Cannot update reply status! because comment doesn't owned this reply!");

        reply.setStatus(status);
        reply.setUpdatedAt(LocalDateTime.now());
        replyRepository.save(reply);
        log.debug("Updating eply status with id of {} success to {}", reply.getId(), status);
    }

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public Reply getById(int replyId) throws ResourceNotFoundException {
        return replyRepository.findById(replyId).orElseThrow(() -> new ResourceNotFoundException("Reply with id of " + replyId + " does not exists!"));
    }

    @Override
    public List<Reply> getAll() {
        return replyRepository.findAll().stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<Reply> getAllById(List<Integer> ids) {
        return replyRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }
}

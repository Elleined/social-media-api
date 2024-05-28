package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.exception.CommentSectionException;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.field.FieldException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.main.post.PostServiceRestriction;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.pin.PostPinCommentService;
import com.elleined.socialmediaapi.service.user.UserServiceRestriction;
import com.elleined.socialmediaapi.validator.FieldValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService, CommentServiceRestriction {
    private final BlockService blockService;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final PostPinCommentService postPinCommentService;

    private final MentionService mentionService;

    private final UserServiceRestriction userServiceRestriction;
    private final PostServiceRestriction postServiceRestriction;

    private final FieldValidator fieldValidator;

    @Override
    public Comment save(User currentUser,
                        Post post,
                        String body,
                        MultipartFile attachedPicture,
                        Set<User> mentionedUsers, Set<HashTag> hashTags) {

        if (fieldValidator.isNotValid(body))
            throw new FieldException("Cannot save comment! because comment body cannot be empty! Please provide text for your comment");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot save comment! because the post you trying to comment is either be deleted or does not exists anymore!");

        if (postServiceRestriction.isCommentSectionClosed(post))
            throw new CommentSectionException("Cannot save comment! because cannot comment because author already closed the comment section for this post!");

        if (blockService.isBlockedByYou(currentUser, post.getCreator()))
            throw new BlockedException("Cannot save comment! because cannot comment because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, post.getCreator()))
            throw new BlockedException("Cannot save comment! because cannot comment because this user block you already!");

        Set<Mention> mentions = mentionService.saveAll(currentUser, mentionedUsers);

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Comment comment = commentMapper.toEntity(currentUser, post, body, picture, mentions, hashTags);
        commentRepository.save(comment);

        log.debug("Comment with id of {} saved successfully", comment.getId());
        return comment;
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment) {
        if (userServiceRestriction.notOwned(currentUser, comment))
            throw new ResourceNotOwnedException("Cannot delete comment! because user with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotOwnedException("Cannot delete comment! because post with id of " + post.getId() + " does not have comment with id of " + comment.getId());

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot delete comment! because this comment might already been deleted or doesn't exists!");

        if (post.getPinnedComment() != null && post.getPinnedComment().equals(comment))
            postPinCommentService.unpin(post);

        updateStatus(currentUser, post, comment, Forum.Status.INACTIVE);
        log.debug("Comment with id of {} and related replies are now inactive!", comment.getId());
    }

    @Override
    public List<Comment> getAll(User currentUser, Post post, Pageable pageable) throws ResourceNotFoundException {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot save comment! because the post you trying to comment is either be deleted or does not exists anymore!");

        if (postServiceRestriction.isCommentSectionClosed(post))
            throw new CommentSectionException("Cannot save comment! because cannot comment because author already closed the comment section for this post!");

        Comment pinnedComment = post.getPinnedComment();
        List<Comment> comments = new java.util.ArrayList<>(postRepository.findAllComments(post, pageable).stream()
                .filter(Comment::isActive)
                .filter(comment -> !comment.equals(pinnedComment))
                .filter(comment -> !blockService.isBlockedByYou(currentUser, comment.getCreator()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
                .toList());

        if (fieldValidator.isValid(pinnedComment)) // Prioritizing pinned comment
            comments.add(0, pinnedComment);

        return comments;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getById(int commentId) throws ResourceNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with id of " + commentId + " does not exists!"));
    }

    @Override
    public List<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Comment> getAllById(List<Integer> ids) {
        return commentRepository.findAllById(ids).stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public void update(User currentUser, Post post, Comment comment, String newBody, MultipartFile newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException {

        if (comment.getBody().equals(newBody)) return;

        if (userServiceRestriction.notOwned(currentUser, comment))
            throw new ResourceNotOwnedException("Cannot update comment! because user with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotFoundException("Cannot update comment! because comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot update comment! because this comment might already been deleted or doesn't exists!");

        String picture = newAttachedPicture == null ? null : newAttachedPicture.getOriginalFilename();

        comment.setBody(newBody);
        comment.setAttachedPicture(picture);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
        log.debug("Updating comment success");
    }

    @Override
    public void reactivate(User currentUser, Post post, Comment comment) {
        if (comment.isActive())
            throw new ResourceNotFoundException("Cannot reactivate comment! because this comment are not deleted or doesn't exists!");

        if (userServiceRestriction.notOwned(currentUser, comment))
            throw new ResourceNotOwnedException("Cannot reactivate comment! because user with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot reactivate comment! because post might already been deleted or doesn't exists!");

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotFoundException("Cannot reactivate comment! because comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());

        updateStatus(currentUser, post, comment, Forum.Status.ACTIVE);
        log.debug("Comment with id of {} and related replies are now active!", comment.getId());
    }

    @Override
    public void updateStatus(User currentUser, Post post, Comment comment, Forum.Status status) {
        if (userServiceRestriction.notOwned(currentUser, comment))
            throw new ResourceNotOwnedException("Cannot update comment status! because user with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        if (postServiceRestriction.notOwned(post, comment))
            throw new ResourceNotFoundException("Cannot update comment status! because comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());

        comment.setStatus(status);
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        log.debug("Updating comment status with id of {} success to {}", comment.getId(), status);
    }
}

package com.elleined.socialmediaapi.service.comment;

import com.elleined.socialmediaapi.exception.*;
import com.elleined.socialmediaapi.mapper.CommentMapper;
import com.elleined.socialmediaapi.model.*;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.ReplyRepository;
import com.elleined.socialmediaapi.service.ModalTrackerService;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.entity.CommentHashTagService;
import com.elleined.socialmediaapi.service.mention.CommentMentionService;
import com.elleined.socialmediaapi.service.pin.PostPinCommentService;
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
public class CommentServiceImpl implements CommentService {
    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final ReplyRepository replyRepository;

    private final PostPinCommentService postPinCommentService;

    private final CommentMentionService commentMentionService;

    private final CommentHashTagService commentHashTagService;

    private final Validator validator;

    @Override
    public Comment save(User currentUser,
                        Post post,
                        String body,
                        MultipartFile attachedPicture,
                        Set<User> mentionedUsers,
                        Set<String> keywords)
            throws ResourceNotFoundException,
            ClosedCommentSectionException,
            BlockedException,
            EmptyBodyException,
            IOException {

        if (validator.isNotValid(body)) throw new EmptyBodyException("Comment body cannot be empty! Please provide text for your comment");
        if (post.isCommentSectionClosed()) throw new ClosedCommentSectionException("Cannot comment because author already closed the comment section for this post!");
        if (post.isInactive()) throw new ResourceNotFoundException("The post you trying to comment is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because this user block you already!");

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        NotificationStatus status = modalTrackerService.isModalOpen(post.getAuthor().getId(), post.getId(), ModalTracker.Type.COMMENT) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        Comment comment = commentMapper.toEntity(body, post, currentUser, picture, status);
        commentRepository.save(comment);

        if (validator.isValid(mentionedUsers)) commentMentionService.mentionAll(currentUser, mentionedUsers, comment);
        if (validator.isValid(keywords)) commentHashTagService.saveAll(comment, keywords);
        log.debug("Comment with id of {} saved successfully", comment.getId());
        return comment;
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment) {
        if (post.doesNotHave(comment)) throw new NotOwnedException("Post with id of " + post.getId() + " does not have comment with id of " + comment.getId());
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setStatus(Status.INACTIVE);
        commentRepository.save(comment);

        if (post.getPinnedComment() != null && post.getPinnedComment().equals(comment))
            postPinCommentService.unpin(post);

        List<Reply> replies = comment.getReplies();
        replies.forEach(reply -> reply.setStatus(Status.INACTIVE));
        replyRepository.saveAll(replies);
        log.debug("Comment with id of {} are now inactive!", comment.getId());
    }

    @Override
    public List<Comment> getAllByPost(User currentUser, Post post) throws ResourceNotFoundException {
        Comment pinnedComment = post.getPinnedComment();
        List<Comment> comments = new ArrayList<>(post.getComments()
                .stream()
                .filter(Comment::isActive)
                .filter(comment -> !comment.equals(pinnedComment))
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
                .sorted(Comparator.comparingInt(Comment::getUpvoteCount).reversed())
                .toList());
        if (pinnedComment != null) comments.add(0, pinnedComment); // Prioritizing pinned comment
        return comments;
    }

    @Override
    public Comment getById(int commentId) throws ResourceNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with id of " + commentId + " does not exists!"));
    }

    @Override
    public Comment updateBody(User currentUser, Post post, Comment comment, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        if (comment.getBody().equals(newBody)) return comment;
        if (post.doesNotHave(comment)) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setBody(newBody);
        commentRepository.save(comment);
        log.debug("Comment with id of {} updated with the new body of {}", comment.getId(), newBody);
        return comment;
    }

    @Override
    public int getTotalReplies(Comment comment) {
        return (int) comment.getReplies().stream()
                .filter(Reply::isActive)
                .count();
    }
}

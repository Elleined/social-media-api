package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.exception.CommentSectionException;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.field.FieldException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.CommentRepository;
import com.elleined.socialmediaapi.repository.main.ReplyRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import com.elleined.socialmediaapi.service.mention.MentionService;
import com.elleined.socialmediaapi.service.pin.PostPinCommentService;
import com.elleined.socialmediaapi.utility.FieldUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final BlockService blockService;

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final ReplyRepository replyRepository;

    private final PostPinCommentService postPinCommentService;

    private final MentionService mentionService;
    private final HashTagService hashTagService;

    @Override
    public Comment save(User currentUser,
                        Post post,
                        String body,
                        MultipartFile attachedPicture,
                        Set<User> mentionedUsers,
                        Set<String> keywords) {

        if (FieldUtil.isNotValid(body)) throw new FieldException("Comment body cannot be empty! Please provide text for your comment");
        if (post.isCommentSectionClosed()) throw new CommentSectionException("Cannot comment because author already closed the comment section for this post!");
        if (post.isInactive()) throw new ResourceNotFoundException("The post you trying to comment is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, post.getCreator())) throw new BlockedException("Cannot comment because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getCreator())) throw new BlockedException("Cannot comment because this user block you already!");

        Set<Mention> mentions = mentionService.saveAll(currentUser, mentionedUsers);
        Set<HashTag> hashTags = hashTagService.saveAll(keywords);

        String picture = attachedPicture == null ? null : attachedPicture.getOriginalFilename();
        Comment comment = commentMapper.toEntity(currentUser, post, body, picture, hashTags, mentions);
        commentRepository.save(comment);

        log.debug("Comment with id of {} saved successfully", comment.getId());
        return comment;
    }

    @Override
    public void delete(User currentUser, Post post, Comment comment) {
        if (post.notOwned(comment)) throw new ResourceNotOwnedException("Post with id of " + post.getId() + " does not have comment with id of " + comment.getId());
        if (currentUser.notOwned(comment)) throw new ResourceNotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setStatus(Forum.Status.INACTIVE);
        commentRepository.save(comment);

        if (post.getPinnedComment() != null && post.getPinnedComment().equals(comment))
            postPinCommentService.unpin(post);

        List<Reply> replies = comment.getReplies();
        replies.forEach(reply -> reply.setStatus(Forum.Status.INACTIVE));
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
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCreator()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
                .toList());
        if (pinnedComment != null) comments.add(0, pinnedComment); // Prioritizing pinned comment
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
    public List<Comment> getAllById(List<Integer> ids) {
        return commentRepository.findAllById(ids);
    }

    @Override
    public Comment update(User currentUser, Post post, Comment comment, String newBody, String newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException {

        if (comment.getBody().equals(newBody)) return comment;
        if (post.notOwned(comment)) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());
        if (currentUser.notOwned(comment)) throw new ResourceNotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setBody(newBody);
        comment.setAttachedPicture(newAttachedPicture);
        commentRepository.save(comment);
        log.debug("Updating comment success");
        return comment;
    }
}

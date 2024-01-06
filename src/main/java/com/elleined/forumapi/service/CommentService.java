package com.elleined.forumapi.service;

import com.elleined.forumapi.exception.*;
import com.elleined.forumapi.mapper.CommentMapper;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.repository.CommentRepository;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.repository.ReplyRepository;
import com.elleined.forumapi.repository.UserRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService
        implements PinService<Comment, Reply>,
        MentionService<Comment> {

    private final UserRepository userRepository;

    private final BlockService blockService;

    private final ModalTrackerService modalTrackerService;

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final ReplyRepository replyRepository;

    private final ImageUploader imageUploader;

    private final PinService<Post, Comment> commentPinService;

    private final MentionRepository mentionRepository;

    @Value("${cropTrade.img.directory}")
    private String cropTradeImgDirectory;

    public Comment save(User currentUser, Post post, String body, MultipartFile attachedPicture, Set<User> mentionedUsers)
            throws ResourceNotFoundException,
            ClosedCommentSectionException,
            BlockedException,
            EmptyBodyException,
            IOException {

        if (StringValidator.isNotValidBody(body)) throw new EmptyBodyException("Comment body cannot be empty! Please provide text for your comment");
        if (post.isCommentSectionClosed()) throw new ClosedCommentSectionException("Cannot comment because author already closed the comment section for this post!");
        if (post.isDeleted()) throw new ResourceNotFoundException("The post you trying to comment is either be deleted or does not exists anymore!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor())) throw new BlockedException("Cannot comment because this user block you already!");

        NotificationStatus status = modalTrackerService.isModalOpen(post.getAuthor().getId(), post.getId(), ModalTracker.Type.COMMENT)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;
        Comment comment = commentMapper.toEntity(body, post, currentUser, attachedPicture.getOriginalFilename(), status);
        commentRepository.save(comment);

        imageUploader.upload(cropTradeImgDirectory + DirectoryFolders.COMMENT_PICTURE_FOLDER, attachedPicture);
        if (mentionedUsers != null) mentionAll(currentUser, mentionedUsers, comment);
        log.debug("Comment with id of {} saved successfully", comment.getId());
        return comment;
    }

    public void delete(User currentUser, Post post, Comment comment) {
        if (post.doesNotHave(comment)) throw new NotOwnedException("Post with id of " + post.getId() + " does not have comment with id of " + comment.getId());
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setStatus(Status.INACTIVE);
        commentRepository.save(comment);

        if (post.getPinnedComment() != null && post.getPinnedComment().equals(comment)) commentPinService.unpin(comment);

        List<Reply> replies = comment.getReplies();
        replies.forEach(reply -> reply.setStatus(Status.INACTIVE));
        replyRepository.saveAll(replies);
        log.debug("Comment with id of {} are now inactive!", comment.getId());
    }

    public List<Comment> getAllByPost(User currentUser, Post post) throws ResourceNotFoundException {
        Comment pinnedComment = post.getPinnedComment();
        List<Comment> comments = new ArrayList<>(post.getComments()
                .stream()
                .filter(comment -> !comment.equals(pinnedComment))
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
                .sorted(Comparator.comparingInt(Comment::getUpvoteCount).reversed())
                .toList());
        if (pinnedComment != null) comments.add(0, pinnedComment); // Prioritizing pinned comment
        return comments;
    }

    public Comment getById(int commentId) throws ResourceNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with id of " + commentId + " does not exists!"));
    }

    public Comment updateUpvote(User respondent, Comment comment)
            throws ResourceNotFoundException,
            UpvoteException {

        if (comment.isDeleted()) throw new ResourceNotFoundException("The comment you trying to upvote might be deleted by the author or does not exists anymore!");
        if (respondent.isAlreadyUpvoted(comment)) throw new UpvoteException("You can only up vote and down vote a comment once!");

        comment.getUpvotingUsers().add(respondent);
        respondent.getUpvotedComments().add(comment);

        commentRepository.save(comment);
        userRepository.save(respondent);
        log.debug("User with id of {} upvoted the Comment with id of {} successfully", respondent.getId(), comment.getId());
        return comment;
    }

    public Comment updateBody(User currentUser, Post post, Comment comment, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException {

        if (post.doesNotHave(comment)) throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " are not associated with post with id of " + post.getId());
        if (comment.getBody().equals(newBody)) return comment;
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " doesn't have comment with id of " + comment.getId());

        comment.setBody(newBody);
        commentRepository.save(comment);
        log.debug("Comment with id of {} updated with the new body of {}", comment.getId(), newBody);
        return comment;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int getTotalReplies(Comment comment) {
        return (int) comment.getReplies().stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .count();
    }

    public Optional<Reply> getPinnedReply(Comment comment) throws ResourceNotFoundException {
        Reply pinnedReply = comment.getPinnedReply();
        if (comment.isDeleted())
            throw new ResourceNotFoundException("Comment with id of " + comment.getId() + " might already been deleted or does not exists!");

        if (pinnedReply == null) return Optional.empty();
        return Optional.of( pinnedReply );
    }

    @Override
    public void pin(User currentUser, Comment comment, Reply reply) throws NotOwnedException, ResourceNotFoundException {
        if (currentUser.notOwned(comment)) throw new NotOwnedException("User with id of " + currentUser.getId() + " does not owned comment with id of " + comment.getId() + " for him/her to pin a reply in this comment!");
        if (comment.doesNotHave(reply)) throw new NotOwnedException("Comment with id of " + comment.getId() + " doesnt have reply of " + reply.getId());
        if (reply.isDeleted()) throw new ResourceNotFoundException("Reply with id of " + reply.getId() + " you specify is already deleted or does not exists anymore!");

        comment.setPinnedReply(reply);
        commentRepository.save(comment);
        log.debug("Comment author with id of {} pinned reply with id of {} in his/her comment with id of {}", comment.getCommenter().getId(), reply.getId(), comment.getId());
    }

    @Override
    public void unpin(Reply reply) {
        reply.getComment().setPinnedReply(null);
        replyRepository.save(reply);
        log.debug("Comment pinned reply unpinned successfully");
    }

    @Override
    public CommentMention mention(User mentioningUser, User mentionedUser, Comment comment) {
        if (comment.isDeleted()) throw new ResourceNotFoundException("Cannot mention! The comment with id of " + comment.getId() + " you are trying to mention might already been deleted or does not exists!");
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

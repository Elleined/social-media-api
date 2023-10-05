package com.elleined.forumapi.service;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final UserService userService;
    private final ReplyService replyService;
    private final BlockService blockService;
    private final ModalTrackerService modalTrackerService;
    private final CommentRepository commentRepository;

    Comment save(User currentUser, Post post, String body, MultipartFile attachedPicture) throws ResourceNotFoundException {
        NotificationStatus status = modalTrackerService.isModalOpen(post.getAuthor().getId(), post.getId(), ModalTracker.Type.COMMENT)
                ? NotificationStatus.READ
                : NotificationStatus.UNREAD;

        Comment comment = Comment.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .post(post)
                .commenter(currentUser)
                .attachedPicture(attachedPicture.isEmpty() ? null : attachedPicture.getOriginalFilename())
                .notificationStatus(status)
                .status(Status.ACTIVE)
                .replies(new ArrayList<>())
                .likes(new HashSet<>())
                .mentions(new HashSet<>())
                .build();

        currentUser.getComments().add(comment);
        post.getComments().add(comment);
        commentRepository.save(comment);
        log.debug("Comment with id of {} saved successfully", comment.getId());
        return comment;
    }

    void delete(Comment comment) {
        comment.setStatus(Status.INACTIVE);
        commentRepository.save(comment);

        log.debug("Comment with id of {} are now inactive!", comment.getId());
        comment.getReplies().forEach(replyService::delete);
    }

    void unpin(Comment comment) {
        comment.getPost().setPinnedComment(null);
        commentRepository.save(comment);

        log.debug("Post pinned comment unpinned successfully");
    }

    void pinReply(Comment comment, Reply reply) {
        comment.setPinnedReply(reply);
        commentRepository.save(comment);
        log.debug("Comment author with id of {} pinned reply with id of {} in his/her comment with id of {}", comment.getCommenter().getId(), reply.getId(), comment.getId());
    }

    List<Comment> getAllByPost(User currentUser, Post post) throws ResourceNotFoundException {
        Comment pinnedComment = post.getPinnedComment();
        List<Comment> comments = new ArrayList<>(post.getComments()
                .stream()
                .filter(comment -> !comment.equals(pinnedComment))
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
                .sorted(Comparator.comparingInt(Comment::getUpvote).reversed())
                .toList());
        if (pinnedComment != null) comments.add(0, pinnedComment); // Prioritizing pinned comment
        return comments;
    }

    public Comment getById(int commentId) throws ResourceNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment with id of " + commentId + " does not exists!"));
    }

    /**
     * @param author alias for currentUser
     */
    public int getNotificationCountForRespondent(User author, int postId, int respondentId) throws ResourceNotFoundException {
        Post post = author.getPosts().stream()
                .filter(userPost -> userPost.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Author with id of " + author.getId() + " does not have post with id of " + postId));

        return (int) post.getComments()
                .stream()
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .filter(comment -> comment.getNotificationStatus() == NotificationStatus.UNREAD)
                .filter(comment -> !blockService.isBlockedBy(author, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(author, comment.getCommenter()))
                .filter(comment -> comment.getCommenter().getId() == respondentId)
                .count();
    }

    public Set<Comment> getUnreadCommentsOfAllPost(User currentUser) {
        List<Post> posts = currentUser.getPosts();

        return posts.stream()
                .map(Post::getComments)
                .flatMap(comments -> comments.stream()
                        .filter(comment -> comment.getStatus() == Status.ACTIVE)
                        .filter(comment -> comment.getNotificationStatus() == NotificationStatus.UNREAD)
                        .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                        .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())))
                .collect(Collectors.toSet());
    }

    void updateUpvote(User respondent, Comment comment) {
        comment.setUpvote(comment.getUpvote() + 1);
        commentRepository.save(comment);

        respondent.getUpvotedComments().add(comment);
        userService.save(respondent);
        log.debug("User with id of {} upvoted the Comment with id of {} successfully", respondent.getId(), comment.getId());
    }

    void updateBody(Comment comment, String newBody) throws ResourceNotFoundException {
        comment.setBody(newBody);
        commentRepository.save(comment);
        log.debug("Comment with id of {} updated with the new body of {}", comment.getId(), newBody);
    }


    private void readComment(Comment comment) {
        comment.setNotificationStatus(NotificationStatus.READ);
    }

    void readAllComments(User currentUser, Post post) {
        if (!currentUser.equals(post.getAuthor())) {
            log.trace("Will not mark as unread because the current user with id of {} are not the author of the post who is {}", currentUser.getId(), post.getAuthor().getId());
            return;
        }
        log.trace("Will mark all as read becuase the current user with id of {} is the author of the post {}", currentUser.getId(), post.getAuthor().getId());
        List<Comment> comments = post.getComments()
                .stream()
                .filter(comment -> comment.getStatus() == Status.ACTIVE)
                .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter()))
                .toList();

        comments.forEach(this::readComment);
        commentRepository.saveAll(comments);
        log.debug("Comments in post with id of {} read successfully!", post.getId());
    }


    boolean isUserAlreadyUpvoteComment(User respondent, int commentId) throws ResourceNotFoundException {
        return respondent.getUpvotedComments()
                .stream()
                .anyMatch(upvotedComment -> upvotedComment.getId() == commentId);
    }

    boolean isCommentSectionClosed(Comment comment) {
        Post post = comment.getPost();
        return post.getCommentSectionStatus() == Post.CommentSectionStatus.CLOSED;
    }

    boolean isUserNotOwnedComment(User currentUser, Comment comment) {
        return currentUser.getComments().stream().noneMatch(comment::equals);
    }

    boolean isHasReply(Comment comment, Reply reply) {
        return comment.getReplies().stream().anyMatch(reply::equals);
    }

    boolean isDeleted(Comment comment) {
        return comment.getStatus() == Status.INACTIVE;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int getTotalReplies(Comment comment) {
        return (int) comment.getReplies().stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .count();
    }
}

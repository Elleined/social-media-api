package com.elleined.forumapi.service;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final ModalTrackerService modalTrackerService;
    private final BlockService blockService;

    Reply save(User currentUser, Comment comment, String body, MultipartFile attachedPicture) throws ResourceNotFoundException {
        NotificationStatus status = modalTrackerService.isModalOpen(comment.getCommenter().getId(), comment.getId(), ModalTracker.Type.REPLY) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        Reply reply = Reply.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .replier(currentUser)
                .comment(comment)
                .attachedPicture(attachedPicture.isEmpty() ? null : attachedPicture.getOriginalFilename())
                .status(Status.ACTIVE)
                .notificationStatus(status)
                .mentions(new HashSet<>())
                .likes(new HashSet<>())
                .build();

        currentUser.getReplies().add(reply);
        comment.getReplies().add(reply);
        replyRepository.save(reply);
        log.debug("Reply with id of {} saved successfully!", reply.getId());
        return reply;
    }

    void delete(Reply reply) {
        reply.setStatus(Status.INACTIVE);
        replyRepository.save(reply);
        log.debug("Reply with id of {} are now inactive!", reply.getId());
    }

    void unpin(Reply reply) {
        reply.getComment().setPinnedReply(null);
        replyRepository.save(reply);
        log.debug("Comment pinned reply unpinned successfully");
    }

    boolean isUserNotOwnedReply(User currentUser, Reply reply) {
        return currentUser.getReplies().stream().noneMatch(reply::equals);
    }

    void updateReplyBody(Reply reply, String newReplyBody) {
        reply.setBody(newReplyBody);
        replyRepository.save(reply);
        log.debug("Reply with id of {} updated with the new body of {}", reply.getId(), newReplyBody);
    }

    private void readReply(Reply reply) {
        reply.setNotificationStatus(NotificationStatus.READ);
    }

    public void readAllReplies(User currentUser, Comment comment) {
        if (!currentUser.equals(comment.getCommenter())) {
            log.trace("Will not mark as unread because the current user with id of {} are not the commenter of the comment {}", currentUser.getId(), comment.getCommenter().getId());
            return;
        }
        log.trace("Will mark all as read because the current user with id of {} is the commenter of the comment {}", currentUser.getId(), comment.getCommenter().getId());
        List<Reply> replies = comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .toList();

        replies.forEach(this::readReply);
        replyRepository.saveAll(replies);
        log.debug("Replies in comment with id of {} read successfully!", comment.getId());
    }

    List<Reply> getAllByComment(User currentUser, Comment comment) {
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

    Set<Reply> getUnreadRepliesOfAllComments(User currentUser) {
        List<Comment> comments = currentUser.getComments();
        return comments.stream()
                .map(Comment::getReplies)
                .flatMap(replies -> replies.stream()
                        .filter(reply -> reply.getStatus() == Status.ACTIVE)
                        .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                        .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                        .filter(reply -> reply.getNotificationStatus() == NotificationStatus.UNREAD))
                .collect(Collectors.toSet());
    }

    /**
     * @param commenter alias for currentUser
     */
    public int getNotificationCountForRespondent(User commenter, int commentId, int respondentId) throws ResourceNotFoundException {
        Comment comment = commenter.getComments()
                .stream()
                .filter(userComment -> userComment.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Commenter with id of " + commenter.getId() + " does not have a comment with id of " + commentId));

        return (int) comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> reply.getNotificationStatus() == NotificationStatus.UNREAD)
                .filter(reply -> !blockService.isBlockedBy(commenter, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(commenter, reply.getReplier()))
                .filter(reply -> reply.getReplier().getId() == respondentId)
                .count();
    }

    boolean isDeleted(Reply reply) {
        return reply.getStatus() == Status.INACTIVE;
    }
}

package com.forum.application.service;

import com.forum.application.exception.ResourceNotFoundException;
import com.forum.application.model.*;
import com.forum.application.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    private final ModalTrackerService modalTrackerService;
    private final BlockService blockService;

    Reply save(User currentUser, Comment comment, String body,String attachedPicture) throws ResourceNotFoundException {
        NotificationStatus status = modalTrackerService.isModalOpen(comment.getCommenter().getId(), comment.getId(), ModalTracker.Type.REPLY) ? NotificationStatus.READ : NotificationStatus.UNREAD;
        Reply reply = Reply.builder()
                .body(body)
                .dateCreated(LocalDateTime.now())
                .replier(currentUser)
                .comment(comment)
                .attachedPicture(attachedPicture)
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

    List<Reply> getAllRepliesOf(User currentUser, Comment comment) {
        return comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .sorted(Comparator.comparing(Reply::getDateCreated))
                .toList();
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

    public int getNotificationCountForRespondent(User currentUser, int commentId, int respondentId) throws ResourceNotFoundException {
        Comment comment = currentUser.getComments()
                .stream()
                .filter(userComment -> userComment.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Commenter with id of " + currentUser.getId() + " does not have a comment with id of " + commentId));

        return (int) comment.getReplies()
                .stream()
                .filter(reply -> reply.getStatus() == Status.ACTIVE)
                .filter(reply -> reply.getNotificationStatus() == NotificationStatus.UNREAD)
                .filter(reply -> !blockService.isBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> !blockService.isYouBeenBlockedBy(currentUser, reply.getReplier()))
                .filter(reply -> reply.getReplier().getId() == respondentId)
                .count();
    }

    boolean isDeleted(Reply reply) {
        return reply.getStatus() == Status.INACTIVE;
    }
}

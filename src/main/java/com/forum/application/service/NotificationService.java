package com.forum.application.service;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.notification.CommentNotification;
import com.forum.application.dto.notification.Notification;
import com.forum.application.dto.notification.PostNotification;
import com.forum.application.dto.notification.ReplyNotification;
import com.forum.application.mapper.CommentMapper;
import com.forum.application.mapper.NotificationMapper;
import com.forum.application.mapper.ReplyMapper;
import com.forum.application.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MentionService mentionService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final ReplyService replyService;
    private final NotificationMapper notificationMapper;

    private final CommentMapper commentMapper;
    private final ReplyMapper replyMapper;

    public Set<Notification> getAllNotification(User currentUser) {
        Set<CommentNotification> unreadComments = commentService.getUnreadCommentsOfAllPost(currentUser).stream()
                .map(commentMapper::toDTO)
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReply = replyService.getUnreadRepliesOfAllComments(currentUser).stream()
                .map(replyMapper::toDTO)
                .map(notificationMapper::toNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostLikes = likeService.getUnreadPostLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentLikes = likeService.getUnreadCommentLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyLikes = likeService.getUnreadReplyLikes(currentUser).stream()
                .map(notificationMapper::toLikeNotification)
                .collect(Collectors.toSet());

        Set<PostNotification> unreadPostMentions = mentionService.getUnreadPostMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<CommentNotification> unreadCommentMentions = mentionService.getUnreadCommentMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        Set<ReplyNotification> unreadReplyMentions = mentionService.getUnreadReplyMentions(currentUser).stream()
                .map(notificationMapper::toMentionNotification)
                .collect(Collectors.toSet());

        // mention notification here
        return Stream.of(unreadComments,
                        unreadReply,
                        unreadPostLikes,
                        unreadCommentLikes,
                        unreadReplyLikes,
                        unreadPostMentions,
                        unreadCommentMentions,
                        unreadReplyMentions
                ).flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public long getTotalNotificationCount(User currentUser) {
        return commentService.getUnreadCommentsOfAllPost(currentUser).size() +
                replyService.getUnreadRepliesOfAllComments(currentUser).size() +
                likeService.getUnreadPostLikes(currentUser).size() +
                likeService.getUnreadCommentLikes(currentUser).size() +
                likeService.getUnreadReplyLikes(currentUser).size() +
                mentionService.getUnreadPostMentions(currentUser).size() +
                mentionService.getUnreadCommentMentions(currentUser).size() +
                mentionService.getUnreadReplyMentions(currentUser).size();
    }

    public CommentNotification getNotification(CommentDTO commentDTO) {
        return notificationMapper.toNotification(commentDTO);
    }

    public ReplyNotification getNotification(ReplyDTO replyDTO) {
        return notificationMapper.toNotification(replyDTO);
    }
}

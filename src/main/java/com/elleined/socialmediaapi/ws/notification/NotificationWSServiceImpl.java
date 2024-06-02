package com.elleined.socialmediaapi.ws.notification;

import com.elleined.socialmediaapi.dto.notification.follow.FollowerNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.post.SharedPostNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.CommentReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.PostReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.ReplyReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.StoryReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.vote.VoteNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationWSServiceImpl implements NotificationWSService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notifyOnComment(CommentNotificationDTO commentNotificationDTO) {
        if (commentNotificationDTO.isRead())
            return;

        int receiverId = commentNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, commentNotificationDTO);
        log.debug("Broadcasting comment notification success");
    }

    @Override
    public void notifyOnReply(ReplyNotificationDTO replyNotificationDTO) {
        if (replyNotificationDTO.isRead())
            return;

        int receiverId = replyNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, replyNotificationDTO);
        log.debug("Broadcasting reply notification success");
    }

    @Override
    public void notifyOnReaction(PostReactionNotificationDTO postReactionNotificationDTO) {
        if (postReactionNotificationDTO.isRead())
            return;

        int receiverId = postReactionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, postReactionNotificationDTO);
        log.debug("Broadcasting post reaction notification success");
    }

    @Override
    public void notifyOnReaction(CommentReactionNotificationDTO commentReactionNotificationDTO) {
        if (commentReactionNotificationDTO.isRead())
            return;

        int receiverId = commentReactionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, commentReactionNotificationDTO);
        log.debug("Broadcasting comment reaction notification success");
    }

    @Override
    public void notifyOnReaction(ReplyReactionNotificationDTO replyReactionNotificationDTO) {
        if (replyReactionNotificationDTO.isRead())
            return;

        int receiverId = replyReactionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, replyReactionNotificationDTO);
        log.debug("Broadcasting reply reaction notification success");
    }

    @Override
    public void notifyOnReaction(StoryReactionNotificationDTO storyReactionNotificationDTO) {
        if (storyReactionNotificationDTO.isRead())
            return;

        int receiverId = storyReactionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, storyReactionNotificationDTO);
        log.debug("Broadcasting story reaction notification success");
    }

    @Override
    public void notifyOnMentioned(PostMentionNotificationDTO postMentionNotificationDTO) {
        if (postMentionNotificationDTO.isRead())
            return;

        int receiverId = postMentionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, postMentionNotificationDTO);
        log.debug("Broadcasting post mention notification success");
    }

    @Override
    public void notifyOnMentioned(CommentMentionNotificationDTO commentMentionNotificationDTO) {
        if (commentMentionNotificationDTO.isRead())
            return;

        int receiverId = commentMentionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, commentMentionNotificationDTO);
        log.debug("Broadcasting comment mention notification success");
    }

    @Override
    public void notifyOnMentioned(ReplyMentionNotificationDTO replyMentionNotificationDTO) {
        if (replyMentionNotificationDTO.isRead())
            return;

        int receiverId = replyMentionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, replyMentionNotificationDTO);
        log.debug("Broadcasting reply mention notification success");
    }

    @Override
    public void notifyOnMentioned(StoryMentionNotificationDTO storyMentionNotificationDTO) {
        if (storyMentionNotificationDTO.isRead())
            return;

        int receiverId = storyMentionNotificationDTO.getReceiverId();

        final String destination = STR."/sma-notification/users/\{receiverId}";
        simpMessagingTemplate.convertAndSend(destination, storyMentionNotificationDTO);
        log.debug("Broadcasting story mention notification success");
    }

    @Override
    public void notifyOnVote(VoteNotificationDTO voteNotificationDTO) {
         if (voteNotificationDTO.isRead())
            return;

         int receiverId = voteNotificationDTO.getReceiverId();

         final String destination = STR."/sma-notification/users/\{receiverId}";
         simpMessagingTemplate.convertAndSend(destination, voteNotificationDTO);
         log.debug("Broadcasting vote notification");
    }

    @Override
    public void notifyOnShare(SharedPostNotificationDTO sharedPostNotificationDTO) {
         if (sharedPostNotificationDTO.isRead())
            return;

         int receiverId = sharedPostNotificationDTO.getReceiverId();

         final String destination = STR."/sma-notification/users/\{receiverId}";
         simpMessagingTemplate.convertAndSend(destination, sharedPostNotificationDTO);
         log.debug("Broadcasting sharedPost notification");
    }

    @Override
    public void notifyOnFollow(FollowerNotificationDTO followerNotificationDTO) {
         if (followerNotificationDTO.isRead())
            return;

         int receiverId = followerNotificationDTO.getReceiverId();

         final String destination = STR."/sma-notification/users/\{receiverId}";
         simpMessagingTemplate.convertAndSend(destination, followerNotificationDTO);
         log.debug("Broadcasting follower notification");
    }
}

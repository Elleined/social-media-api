package com.elleined.socialmediaapi.ws.notification;

import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.CommentReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.PostReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.ReplyReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.StoryReactionNotificationDTO;

public interface NotificationWSService {
    // Receiver is the post creator
    void notifyOnComment(CommentNotificationDTO commentNotificationDTO);
    // Receiver is the comment creator
    void notifyOnReply(ReplyNotificationDTO replyNotificationDTO);

     // Reaction
     // Receiver is the post creator
     void notifyOnReaction(PostReactionNotificationDTO postReactionNotificationDTO);
     // Receiver is the comment creator
     void notifyOnReaction(CommentReactionNotificationDTO commentReactionNotificationDTO);
     // Receiver is the reply creator
     void notifyOnReaction(ReplyReactionNotificationDTO replyReactionNotificationDTO);
     // Receiver is the story creator
     void notifyOnReaction(StoryReactionNotificationDTO storyReactionNotificationDTO);

     // Mention
     // Receiver is the mentioned user
     void notifyOnMentioned(PostMentionNotificationDTO postMentionNotificationDTO);
     // Receiver is the mentioned user
     void notifyOnMentioned(CommentMentionNotificationDTO commentMentionNotificationDTO);
     // Receiver is the mentioned user
     void notifyOnMentioned(ReplyMentionNotificationDTO replyMentionNotificationDTO);
     // Receiver is the mentioned user
     void notifyOnMentioned(StoryMentionNotificationDTO storyMentionNotificationDTO);

    // Vote
    // // Receiver is the comment creator
    // void notifyOnVote(User creator, Comment comment, Vote vote);

    // Share post
    // // Receiver is the post creator
    // void notifyOnShare(User creator, Post post);

    // Friend Request
    // // Receiver is the requested user
    // void notifyOnReceiveFriendRequest(User creator, FriendRequest friendRequest);

    // // Creator is the friend request requested user
    // // Receiver is the friend request creator
    // void notifyOnAcceptFriendRequest(User creator, FriendRequest friendRequest);

    // Follow
    // // Receiver is the followed user
    // void notifyOnFollow(User creator, User followedUser);
}



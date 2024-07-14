package com.elleined.socialmediaapi.ws.notification;

import com.elleined.socialmediaapi.dto.notification.follow.FollowerNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.friend.FriendRequestNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.post.SharedPostNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.*;
import com.elleined.socialmediaapi.dto.notification.vote.VoteNotificationDTO;

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
    // Receiver is the note creator
    void notifyOnReaction(NoteReactionNotificationDTO noteReactionNotificationDTO);

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
    void notifyOnVote(VoteNotificationDTO voteNotificationDTO);

    // Share post
    // // Receiver is the post creator
    void notifyOnShare(SharedPostNotificationDTO sharedPostNotificationDTO);

    // Follow
    // // Receiver is the followed user
    void notifyOnFollow(FollowerNotificationDTO followerNotificationDTO);

    // Friend Request
    // // Receiver is the requested user
    void notifyOnReceiveFriendRequest(FriendRequestNotificationDTO friendRequestNotificationDTO);

    // // Creator is the friend request requested user
    // // Receiver is the friend request creator
    void notifyOnAcceptFriendRequest(FriendRequestNotificationDTO friendRequestNotificationDTO);
}



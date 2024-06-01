package com.elleined.socialmediaapi.ws.notification;

import com.elleined.socialmediaapi.dto.notification.main.CommentNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;

public interface NotificationWSService {
    // Receiver is the post creator
    void notifyOnComment(CommentNotificationDTO commentNotificationDTO);
    // Receiver is the comment creator
    void notifyOnReply(ReplyNotificationDTO replyNotificationDTO);

    // Reaction
    // // Receiver is the post creator
    // void notifyOnReaction(User creator, Post post, Reaction reaction);
    // // Receiver is the comment creator
    // void notifyOnReaction(User creator, Comment comment, Reaction reaction);
    // // Receiver is the reply creator
    // void notifyOnReaction(User creator, Reply reply, Reaction reaction);
    // // Receiver is the story creator
    // void notifyOnReaction(User creator, Story story, Reaction reaction);

    // Mention
    // // Receiver is the mentioned user
    // void notifyOnMentioned(User creator, User mentionedUser, Post post);
    // // Receiver is the mentioned user
    // void notifyOnMentioned(User creator, User mentionedUser, Comment comment);
    // // Receiver is the mentioned user
    // void notifyOnMentioned(User creator, User mentionedUser, Reply reply);
    // // Receiver is the mentioned user
    // void notifyOnMentioned(User creator, User mentionedUser, Story story);

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



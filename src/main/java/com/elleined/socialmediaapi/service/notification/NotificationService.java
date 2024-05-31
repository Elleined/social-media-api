package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;

public interface NotificationService {
    List<List<? extends Notification>> getAll(User currentUser);

    // Receiver is the post creator
    CommentNotification saveOnComment(User creator, Comment comment);
    // Receiver is the comment creator
    ReplyNotification saveOnReply(User creator, Reply reply);

    // Reaction
    // // Receiver is the post creator
    // void saveOnReaction(User creator, Post post, Reaction reaction);
    // // Receiver is the comment creator
    // void saveOnReaction(User creator, Comment comment, Reaction reaction);
    // // Receiver is the reply creator
    // void saveOnReaction(User creator, Reply reply, Reaction reaction);
    // // Receiver is the story creator
    // void saveOnReaction(User creator, Story story, Reaction reaction);

    // Mention
    // // Receiver is the mentioned user
    // void saveOnMentioned(User creator, User mentionedUser, Post post);
    // // Receiver is the mentioned user
    // void saveOnMentioned(User creator, User mentionedUser, Comment comment);
    // // Receiver is the mentioned user
    // void saveOnMentioned(User creator, User mentionedUser, Reply reply);
    // // Receiver is the mentioned user
    // void saveOnMentioned(User creator, User mentionedUser, Story story);

    // Vote
    // // Receiver is the comment creator
    // void saveOnVote(User creator, Comment comment, Vote vote);

    // Share post
    // // Receiver is the post creator
    // void saveOnShare(User creator, Post post);

    // Friend Request
    // // Receiver is the requested user
    // void saveOnReceiveFriendRequest(User creator, FriendRequest friendRequest);

    // // Creator is the friend request requested user
    // // Receiver is the friend request creator
    // void saveOnAcceptFriendRequest(User creator, FriendRequest friendRequest);

    // Follow
    // // Receiver is the followed user
    // void saveOnFollow(User creator, User followedUser);
}

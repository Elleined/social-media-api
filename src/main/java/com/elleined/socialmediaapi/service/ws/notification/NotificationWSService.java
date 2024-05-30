package com.elleined.socialmediaapi.service.ws.notification;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;

public interface NotificationWSService {
    // Receiver is the post creator
    void notifyOnComment(User creator, Comment comment);
    // Receiver is the comment creator
    void notifyOnReply(User creator, Reply reply);

    // Reaction
    // // Receiver is the post creator
    void notifyOnReaction(User creator, Post post, Reaction reaction);
    // // Receiver is the comment creator
    void notifyOnReaction(User creator, Comment comment, Reaction reaction);
    // // Receiver is the reply creator
    void notifyOnReaction(User creator, Reply reply, Reaction reaction);
    // // Receiver is the story creator
    void notifyOnReaction(User creator, Story story, Reaction reaction);

    // Mention
    // // Receiver is the mentioned user
    void notifyOnMentioned(User creator, User receiver, Post post);
    // // Receiver is the mentioned user
    void notifyOnMentioned(User creator, User receiver, Comment comment);
    // // Receiver is the mentioned user
    void notifyOnMentioned(User creator, User receiver, Reply reply);
    // // Receiver is the mentioned user
    void notifyOnMentioned(User creator, User receiver, Story story);


    // Vote
    // // Receiver is the comment creator
    void notifyOnVote(User creator, Comment comment, Vote vote);

    // Share post
    // // Receiver is the post creator
    void notifyOnShare(User creator, Post post);

    // Friend Request
    // // Receiver is the requested user
    void notifyOnReceiveFriendRequest(User creator, FriendRequest friendRequest);

    // // Creator is the friend request requested user
    // // Receiver is the friend request creator
    void notifyOnAcceptFriendRequest(User creator, FriendRequest friendRequest);

    // Follow
    // // Receiver is the followed user
    void notifyOnFollow(User creator, User followedUser);
}



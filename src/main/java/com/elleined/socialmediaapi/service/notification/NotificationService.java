package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;
import org.w3c.dom.Notation;

import java.util.Collection;
import java.util.List;


public interface NotificationService {
    List<Notification> getAllNotification(User currentUser);
    List<Notification> getAllNotification(User currentUser, Notification.Status status);

    void read(User currentUser, Post post);
    void read(User currentUser, Comment comment);
    void read(User currentUser, Reply reply);
    void read(User currentUser, Mention mention);
    void read(User currentUser, React react);
    void read(User currentUser, FriendRequest friendRequest);
}

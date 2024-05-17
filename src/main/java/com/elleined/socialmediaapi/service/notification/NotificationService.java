package com.elleined.socialmediaapi.service.notification;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;


public interface NotificationService {
    List<Notification> getAllById(Set<Integer> ids);
    List<Notification> getAllNotification(User currentUser);
    List<Notification> getAllNotification(User currentUser, Notification.Status status);

    void read(User currentUser, Post post);
    void read(User currentUser, Comment comment);
    void read(User currentUser, Reply reply);
    void read(User currentUser, Mention mention);
    void read(User currentUser, React react);
    void read(User currentUser, FriendRequest friendRequest);

    default List<Notification> getAllUnreadNotification(User currentUser) {
        return this.getAllNotification(currentUser, Notification.Status.UNREAD);
    }
}

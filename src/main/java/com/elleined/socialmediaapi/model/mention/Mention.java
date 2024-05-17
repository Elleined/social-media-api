package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_mention")
@NoArgsConstructor
@Getter
@Setter
public class Mention extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "mentioned_user_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User mentionedUser;

    @ManyToMany(mappedBy = "mentions")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "mentions")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "mentions")
    private Set<Reply> replies;

    @ManyToMany(mappedBy = "mentions")
    private Set<Notification> notifications;

    @Builder
    public Mention(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   User creator,
                   User mentionedUser,
                   Set<Post> posts,
                   Set<Comment> comments,
                   Set<Reply> replies,
                   Set<Notification> notifications) {
        super(id, createdAt, updatedAt);
        this.creator = creator;
        this.mentionedUser = mentionedUser;
        this.posts = posts;
        this.comments = comments;
        this.replies = replies;
        this.notifications = notifications;
    }


    public Set<Integer> getAllPostIds() {
        return this.getPosts().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllCommentIds() {
        return this.getComments().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllReplyIds() {
        return this.getReplies().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllNotificationIds() {
        return this.getNotifications().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}

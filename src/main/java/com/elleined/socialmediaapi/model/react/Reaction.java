package com.elleined.socialmediaapi.model.react;


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
@Table(name = "tbl_react")
@NoArgsConstructor
@Getter
@Setter
public class Reaction extends PrimaryKeyIdentity {

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
            name = "emoji_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Emoji emoji;

    @ManyToMany(mappedBy = "reactions")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "reactions")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "reactions")
    private Set<Reply> replies;

    @ManyToMany(mappedBy = "reactions")
    private Set<Notification> notifications;

    @Builder
    public Reaction(int id,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    User creator,
                    Emoji emoji,
                    Set<Post> posts,
                    Set<Comment> comments,
                    Set<Reply> replies,
                    Set<Notification> notifications) {
        super(id, createdAt, updatedAt);
        this.creator = creator;
        this.emoji = emoji;
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

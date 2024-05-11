package com.elleined.socialmediaapi.model.react;


import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Reply;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "tbl_react")
@NoArgsConstructor
@Getter
@Setter
public class React extends PrimaryKeyIdentity {


    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @Column(
            name = "notification_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus; // of the post author

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

    @Builder
    public React(int id,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 User creator,
                 NotificationStatus notificationStatus,
                 Emoji emoji,
                 Set<Post> posts,
                 Set<Comment> comments,
                 Set<Reply> replies) {
        super(id, createdAt, updatedAt);
        this.creator = creator;
        this.notificationStatus = notificationStatus;
        this.emoji = emoji;
        this.posts = posts;
        this.comments = comments;
        this.replies = replies;
    }

    public boolean isRead() {
        return this.getNotificationStatus() == NotificationStatus.READ;
    }
    public boolean isUnread() {
        return this.getNotificationStatus() == NotificationStatus.UNREAD;
    }
}

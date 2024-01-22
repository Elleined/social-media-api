package com.elleined.forumapi.model;

import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.model.react.ReplyReact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_reply")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "reply_id",
            unique = true,
            nullable = false,
            updatable = false
    )
    private int id;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "notification_status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Column(name = "attached_picture", columnDefinition = "MEDIUMTEXT")
    private String attachedPicture;

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "comment_id",
            foreignKey = @ForeignKey(name = "FK_comment_id")
    )
    private Comment comment;

    @ManyToOne
    @JoinColumn(
            name = "replier_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "FK_replier_id")
    )
    private User replier;

    @OneToMany(mappedBy = "reply")
    @Setter(AccessLevel.NONE)
    private Set<ReplyMention> mentions;

    // reply id reference is in tbl reply emoji table
    @OneToMany(mappedBy = "reply")
    @Setter(AccessLevel.NONE)
    private List<ReplyReact> reactions;


    @ManyToMany
    @JoinTable(
            name = "tbl_reply_hashtag",
            joinColumns = @JoinColumn(name = "reply_id",
                    referencedColumnName = "reply_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "hashtag_id",
                    referencedColumnName = "id"
            )
    )
    private Set<HashTag> hashTags;

    public boolean isInactive() {
        return this.getStatus() == Status.INACTIVE;
    }
    public boolean isActive() {
        return this.getStatus() == Status.ACTIVE;
    }

    public boolean isRead() {
        return this.getNotificationStatus() == NotificationStatus.READ;
    }

    public boolean isUnread() {
        return this.getNotificationStatus() == NotificationStatus.UNREAD;
    }
}

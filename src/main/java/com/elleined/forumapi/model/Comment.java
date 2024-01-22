package com.elleined.forumapi.model;

import com.elleined.forumapi.model.hashtag.HashTag;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.react.CommentReact;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_comment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "comment_id",
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

    @Column(name = "attached_picture", columnDefinition = "MEDIUMTEXT")
    private String attachedPicture;

    @Column(name = "notification_status")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            foreignKey = @ForeignKey(name = "FK_post_id")
    )
    private Post post;

    @ManyToOne
    @JoinColumn(
            name = "commenter_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "FK_commenter_id")
    )
    private User commenter;

    @OneToOne
    @JoinColumn(
            name = "pinned_reply_id",
            referencedColumnName = "reply_id"
    )
    private Reply pinnedReply;

    @ManyToMany
    @JoinTable(
            name = "tbl_comment_hashtag",
            joinColumns = @JoinColumn(name = "comment_id",
                    referencedColumnName = "comment_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "hashtag_id",
                    referencedColumnName = "id"
            )
    )
    private Set<HashTag> hashTags;

    // comment id reference is in reply table
    @OneToMany(mappedBy = "comment")
    @Setter(AccessLevel.NONE)
    private List<Reply> replies;

    // comment id refernce is in tbl mention comment
    @OneToMany(mappedBy = "comment")
    @Setter(AccessLevel.NONE)
    private Set<CommentMention> mentions;

    // comment id refernce is in tbl comment emoji
    @OneToMany(mappedBy = "comment")
    @Setter(AccessLevel.NONE)
    private List<CommentReact> reactions;

    @ManyToMany(mappedBy = "upvotedComments")
    private Set<User> upvotingUsers;

    public boolean isCommentSectionClosed() {
        return this.getPost().getCommentSectionStatus() == Post.CommentSectionStatus.CLOSED;
    }
    public boolean doesNotHave(Reply reply) {
        return this.getReplies().stream().noneMatch(reply::equals);
    }
    public boolean isInactive() {
        return this.getStatus() == Status.INACTIVE;
    }
    public boolean isActive() {
        return this.getStatus() == Status.ACTIVE;
    }

    public int getUpvoteCount() {
        return this.getUpvotingUsers().size();
    }

    public boolean isRead() {
        return this.getNotificationStatus() == NotificationStatus.READ;
    }

    public boolean isUnread() {
        return this.getNotificationStatus() == NotificationStatus.UNREAD;
    }
}

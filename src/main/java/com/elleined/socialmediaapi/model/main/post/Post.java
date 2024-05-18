package com.elleined.socialmediaapi.model.main.post;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_post")
@NoArgsConstructor
@Getter
@Setter
public class Post extends Forum {

    @Column(
            name = "comment_section_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CommentSectionStatus commentSectionStatus;

    @OneToOne
    @JoinColumn(
            name = "pinned_comment_id",
            referencedColumnName = "id"
    )
    private Comment pinnedComment;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_hashtag",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "hashtag_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<HashTag> hashTags;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_mention",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "mention_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Mention> mentions;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_react",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "react_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Reaction> reactions;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_saved",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> savingUsers;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_share",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> sharers;

    @ManyToMany
    @JoinTable(
            name = "tbl_post_notification",
            joinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "notification_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Notification> notifications;

    @Builder
    public Post(int id,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String body,
                Status status,
                String attachedPicture,
                User creator,
                CommentSectionStatus commentSectionStatus,
                Comment pinnedComment,
                List<Comment> comments,
                Set<HashTag> hashTags,
                Set<Mention> mentions,
                Set<Reaction> reactions,
                Set<User> savingUsers,
                Set<User> sharers,
                Set<Notification> notifications) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creator);
        this.commentSectionStatus = commentSectionStatus;
        this.pinnedComment = pinnedComment;
        this.comments = comments;
        this.hashTags = hashTags;
        this.mentions = mentions;
        this.reactions = reactions;
        this.savingUsers = savingUsers;
        this.sharers = sharers;
        this.notifications = notifications;
    }

    public enum CommentSectionStatus {OPEN, CLOSED}

    public Set<Integer> getAllHashTagIds() {
        return this.getHashTags().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllMentionIds() {
        return this.getMentions().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllReactionIds() {
        return this.getReactions().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public List<Integer> getAllCommentIds() {
        return this.getComments().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
    }

    public Set<Integer> getAllSavingUserIds() {
        return this.getSavingUsers().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllSharerIds() {
        return this.getSharers().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllNotificationIds() {
        return this.getNotifications().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}

package com.elleined.socialmediaapi.model.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.react.React;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "tbl_hashtag_post",
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
            name = "tbl_mention_post",
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
            name = "tbl_react_post",
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
    private Set<React> reactions;

    // post id reference is in comment table
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "savedPosts")
    private Set<User> savingUsers;

    @ManyToMany(mappedBy = "sharedPosts")
    private Set<User> sharers;

    @Builder
    public Post(int id,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String body,
                Status status,
                String attachedPicture,
                User creator,
                NotificationStatus notificationStatus,
                CommentSectionStatus commentSectionStatus,
                Comment pinnedComment,
                Set<HashTag> hashTags,
                Set<Mention> mentions,
                Set<React> reactions,
                List<Comment> comments,
                Set<User> savingUsers,
                Set<User> sharers) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creator, notificationStatus);
        this.commentSectionStatus = commentSectionStatus;
        this.pinnedComment = pinnedComment;
        this.hashTags = hashTags;
        this.mentions = mentions;
        this.reactions = reactions;
        this.comments = comments;
        this.savingUsers = savingUsers;
        this.sharers = sharers;
    }

    public enum CommentSectionStatus {OPEN, CLOSED}

    public boolean isCommentSectionClosed() {
        return this.getCommentSectionStatus() == CommentSectionStatus.CLOSED;
    }
    public boolean isCommentSectionOpen() {
        return this.getCommentSectionStatus() == CommentSectionStatus.OPEN;
    }
    public boolean doesNotHave(Comment comment) {
        return this.getComments().stream().noneMatch(comment::equals);
    }
}

package com.elleined.socialmediaapi.model.main;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.react.React;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "tbl_reply")
@NoArgsConstructor
@Getter
@Setter
public class Reply extends Forum {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Comment comment;

    @ManyToMany
    @JoinTable(
            name = "tbl_reply_hashtag",
            joinColumns = @JoinColumn(
                    name = "reply_id",
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
            name = "tbl_reply_mention",
            joinColumns = @JoinColumn(
                    name = "reply_id",
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
            name = "tbl_reply_react",
            joinColumns = @JoinColumn(
                    name = "reply_id",
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

    @Builder
    public Reply(int id,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt,
                 String body,
                 Status status,
                 String attachedPicture,
                 User creator,
                 NotificationStatus notificationStatus,
                 Comment comment,
                 Set<HashTag> hashTags,
                 Set<Mention> mentions,
                 Set<React> reactions) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creator, notificationStatus);
        this.comment = comment;
        this.hashTags = hashTags;
        this.mentions = mentions;
        this.reactions = reactions;
    }

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
}

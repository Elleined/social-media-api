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
@Table(name = "tbl_comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment extends Forum {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @OneToOne
    @JoinColumn(
            name = "pinned_reply_id",
            referencedColumnName = "id"
    )
    private Reply pinnedReply;

    @ManyToMany
    @JoinTable(
            name = "tbl_hashtag_comment",
            joinColumns = @JoinColumn(
                    name = "comment_id",
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
            name = "tbl_mention_comment",
            joinColumns = @JoinColumn(
                    name = "comment_id",
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
            name = "tbl_react_comment",
            joinColumns = @JoinColumn(
                    name = "comment_id",
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

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;

    @ManyToMany(mappedBy = "votedComments")
    private Set<User> userVotes;

    @Builder
    public Comment(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String body,
                   Status status,
                   String attachedPicture,
                   User creator,
                   NotificationStatus notificationStatus,
                   Post post,
                   Reply pinnedReply,
                   Set<HashTag> hashTags,
                   Set<Mention> mentions,
                   Set<React> reactions,
                   List<Reply> replies,
                   Set<User> userVotes) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creator, notificationStatus);
        this.post = post;
        this.pinnedReply = pinnedReply;
        this.hashTags = hashTags;
        this.mentions = mentions;
        this.reactions = reactions;
        this.replies = replies;
        this.userVotes = userVotes;
    }

    public boolean doesNotHave(Reply reply) {
        return this.getReplies().stream().noneMatch(reply::equals);
    }
}

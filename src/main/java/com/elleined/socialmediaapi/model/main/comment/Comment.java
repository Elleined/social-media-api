package com.elleined.socialmediaapi.model.main.comment;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.react.React;
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
@Table(name = "tbl_comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment extends Forum {

    @OneToOne
    @JoinColumn(
            name = "pinned_reply_id",
            referencedColumnName = "id"
    )
    private Reply pinnedReply;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;

    @ManyToMany
    @JoinTable(
            name = "tbl_comment_hashtag",
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
            name = "tbl_comment_mention",
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
            name = "tbl_comment_react",
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

    @ManyToMany
    @JoinTable(
            name = "tbl_comment_vote",
            joinColumns = @JoinColumn(
                    name = "comment_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> userVotes;


    @ManyToMany
    @JoinTable(
            name = "tbl_comment_notification",
            joinColumns = @JoinColumn(
                    name = "comment_id",
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
    public Comment(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String body,
                   Status status,
                   String attachedPicture,
                   User creator,
                   Reply pinnedReply,
                   Post post,
                   List<Reply> replies,
                   Set<HashTag> hashTags,
                   Set<Mention> mentions,
                   Set<React> reactions,
                   Set<User> userVotes,
                   Set<Notification> notifications) {
        super(id, createdAt, updatedAt, body, status, attachedPicture, creator);
        this.pinnedReply = pinnedReply;
        this.post = post;
        this.replies = replies;
        this.hashTags = hashTags;
        this.mentions = mentions;
        this.reactions = reactions;
        this.userVotes = userVotes;
        this.notifications = notifications;
    }

    public boolean has(Reply reply) {
        return this.getReplies().stream().anyMatch(reply::equals);
    }

    public List<Integer> getAllReplyIds() {
        return this.getReplies().stream()
                .map(PrimaryKeyIdentity::getId)
                .toList();
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
    public Set<Integer> getAllUserVoteIds() {
        return this.getUserVotes().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllNotificationIds() {
        return this.getNotifications().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}

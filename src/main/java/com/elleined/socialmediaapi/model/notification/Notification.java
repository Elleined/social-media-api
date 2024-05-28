package com.elleined.socialmediaapi.model.notification;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.react.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Notification extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "sender_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User receiver;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    private Status status;

    @ManyToMany(mappedBy = "notifications")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "notifications")
    private Set<Reply> replies;

    @ManyToMany
    @JoinTable(
            name = "tbl_react_notification",
            joinColumns = @JoinColumn(
                    name = "notification_id",
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
            name = "tbl_mention_notification",
            joinColumns = @JoinColumn(
                    name = "notification_id",
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
            name = "tbl_friend_request_notification",
            joinColumns = @JoinColumn(
                    name = "notification_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "friend_request_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<FriendRequest> friendRequests;

    public enum Status {
        READ,
        UNREAD
    }

    public Set<Integer> getAllPostIds() {
        return this.getPosts().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllCommentIds() {
        return this.getComments().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllReplyIds() {
        return this.getReplies().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllReactionIds() {
        return this.getReactions().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllMentionIds() {
        return this.getMentions().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllFriendRequestIds() {
        return this.getFriendRequests().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}

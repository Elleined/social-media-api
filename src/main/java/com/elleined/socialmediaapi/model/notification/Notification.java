package com.elleined.socialmediaapi.model.notification;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.react.Reaction;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_notification")
@NoArgsConstructor
@Setter
@Getter
public class Notification extends PrimaryKeyIdentity {

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    private Status status;

    @ManyToMany(mappedBy = "notifications")
    private Set<Post> posts;

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

    @Builder
    public Notification(int id,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,
                        String message,
                        Status status,
                        Set<Post> posts,
                        Set<Comment> comments,
                        Set<Reply> replies,
                        Set<Reaction> reactions,
                        Set<Mention> mentions,
                        Set<FriendRequest> friendRequests) {
        super(id, createdAt, updatedAt);
        this.message = message;
        this.status = status;
        this.posts = posts;
        this.comments = comments;
        this.replies = replies;
        this.reactions = reactions;
        this.mentions = mentions;
        this.friendRequests = friendRequests;
    }

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

package com.elleined.forumapi.model;

import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.model.note.Note;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "tbl_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id",
            unique = true,
            nullable = false,
            updatable = false
    )
    private int id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email_address",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "picture",
            columnDefinition = "MEDIUMTEXT"
    )
    private String picture;

    @Column(
            name = "uuid",
            nullable = false,
            updatable = false,
            unique = true
    )
    private String UUID;

    @ManyToMany
    @JoinTable(
            name = "tbl_comment_upvotes",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "upvoted_comment_id",
                    referencedColumnName = "comment_id"
            )
    )
    private Set<Comment> upvotedComments;

    @ManyToMany
    @JoinTable(
            name = "tbl_blocked_user",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "blocked_user_id",
                    referencedColumnName = "user_id"
            )
    )
    private Set<User> blockedUsers;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_shared_post",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "post_id"
            )
    )
    private Set<Post> sharedPosts;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_saved_post",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "post_id"
            )
    )
    private Set<Post> savedPosts;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_follower",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "follower_user_id",
                    referencedColumnName = "user_id"
            )
    )
    private Set<User> followers;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_following",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "following_user_id",
                    referencedColumnName = "user_id"
            )
    )
    private Set<User> followings;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_friend",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "friend_id",
                    referencedColumnName = "user_id"
            )
    )
    private Set<User> friends;

    // user id reference is in tbl  user friend
    @OneToMany(mappedBy = "requestingUser")
    private Set<FriendRequest> sentFriendRequest;

    // user id reference is in tbl  user friend
    @OneToMany(mappedBy = "requestedUser")
    private Set<FriendRequest> receiveFriendRequest;

    // user id reference is in post table
    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    // user id reference is in comment table
    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments;

    // user id reference is in reply table
    @OneToMany(mappedBy = "replier")
    private List<Reply> replies;

    // user id reference is in tbl mention post
    @OneToMany(mappedBy = "mentioningUser")
    private Set<PostMention> sentPostMentions;

    // user id reference is in tbl mention post
    @OneToMany(mappedBy = "mentionedUser")
    private Set<PostMention> receivePostMentions;

    // user id reference is in tbl mention comment
    @OneToMany(mappedBy = "mentioningUser")
    private Set<CommentMention> sentCommentMentions;

    // user id reference is in tbl mention comment
    @OneToMany(mappedBy = "mentionedUser")
    private Set<CommentMention> receiveCommentMentions;

    // user id reference is in tbl mention reply
    @OneToMany(mappedBy = "mentioningUser")
    private Set<ReplyMention> sentReplyMentions;

    // user id reference is in tbl mention reply
    @OneToMany(mappedBy = "mentionedUser")
    private Set<ReplyMention> receiveReplyMentions;

    // user id reference is in tbl post emoji
    @OneToMany(mappedBy = "respondent")
    private Set<PostReact> createdPostReactions;

    // user id reference is in tbl comment emoji
    @OneToMany(mappedBy = "respondent")
    private Set<CommentReact> createdCommentReactions;

    // user id reference is in tbl reply emoji
    @OneToMany(mappedBy = "respondent")
    private Set<ReplyReact> createdReplyReactions;

    // user id reference is in tbl user note
    @OneToOne(mappedBy = "user")
    private Note note;

    public boolean notOwned(Post post) {
        return this.getPosts().stream().noneMatch(post::equals);
    }
    public boolean notOwned(Comment comment) {
        return this.getComments().stream().noneMatch(comment::equals);
    }
    public boolean notOwned(Reply reply) {
        return this.getReplies().stream().noneMatch(reply::equals);
    }

    public boolean notOwned(PostReact postReact) {
        return this.getCreatedPostReactions().stream().noneMatch(postReact::equals);
    }

    public boolean notOwned(CommentReact commentReact) {
        return this.getCreatedCommentReactions().stream().noneMatch(commentReact::equals);
    }

    public boolean notOwned(ReplyReact replyReact) {
        return this.getCreatedReplyReactions().stream().noneMatch(replyReact::equals);
    }

    public boolean isAlreadyReactedTo(Post post) {
        return this.getCreatedPostReactions().stream()
                .map(PostReact::getPost)
                .anyMatch(post::equals);
    }

    public boolean isAlreadyReactedTo(Comment comment) {
        return this.getCreatedCommentReactions().stream()
                .map(CommentReact::getComment)
                .anyMatch(comment::equals);
    }

    public boolean isAlreadyReactedTo(Reply reply) {
        return this.getCreatedReplyReactions().stream()
                .map(ReplyReact::getReply)
                .anyMatch(reply::equals);
    }

    public boolean isAlreadyUpvoted(Comment comment) {
        return this.getUpvotedComments().stream().anyMatch(comment::equals);
    }

    public boolean isFriendsWith(User anotherUser) {
        return this.getFriends().contains(anotherUser);
    }

    public boolean hasAlreadySentFriendRequestTo(User userToAdd) {
        return this.getSentFriendRequest().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(userToAdd::equals);
    }

    public boolean hasAlreadyReceiveFriendRequestTo(User userToAdd) {
        return userToAdd.getSentFriendRequest().stream()
                .map(FriendRequest::getRequestedUser)
                .anyMatch(this::equals);
    }

    public boolean hasNote() {
        return this.getNote() != null;
    }

    public boolean hasFriendRequest(FriendRequest friendRequest) {
        return this.getReceiveFriendRequest().contains(friendRequest);
    }
}

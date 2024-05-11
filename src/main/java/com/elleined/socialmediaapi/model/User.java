package com.elleined.socialmediaapi.model;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "tbl_user")
@NoArgsConstructor
@Getter
@Setter
public class User extends PrimaryKeyIdentity {

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(name = "picture")
    private String picture;

    @Column(
            name = "uuid",
            nullable = false,
            updatable = false,
            unique = true
    )
    private String UUID;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Set<Comment> votedComments;

    @ManyToMany
    @JoinTable(
            name = "tbl_blocked_user",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "blocked_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> blockedUsers;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_shared_post",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Post> sharedPosts;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_saved_post",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<Post> savedPosts;

    @ManyToMany
    @JoinTable(
            name = "tbl_follower",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "follower_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> followers;

    @ManyToMany
    @JoinTable(
            name = "tbl_following",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "following_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> followings;

    @ManyToMany
    @JoinTable(
            name = "tbl_friend_",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "friend_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> friends;

    // user id reference is in tbl  user friend
    @OneToMany(mappedBy = "creator")
    private Set<FriendRequest> sentFriendRequest;

    // user id reference is in tbl  user friend
    @OneToMany(mappedBy = "requestedUser")
    private Set<FriendRequest> receiveFriendRequest;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;

    @OneToMany(mappedBy = "creator")
    private List<Comment> comments;

    @OneToMany(mappedBy = "creator")
    private List<Reply> replies;

    @OneToOne(mappedBy = "creator")
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

    public boolean isAlreadyUpvoted(Comment comment) {
        return this.getVotedComments().stream().anyMatch(comment::equals);
    }
    public boolean hasNote() {
        return this.getNote() != null;
    }

    public boolean isFriendsWith(User anotherUser) {
        return this.getFriends().contains(anotherUser);
    }
    public boolean hasFriendRequest(FriendRequest friendRequest) {
        return this.getReceiveFriendRequest().contains(friendRequest);
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
}

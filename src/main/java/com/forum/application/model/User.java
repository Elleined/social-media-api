package com.forum.application.model;

import com.forum.application.model.like.CommentLike;
import com.forum.application.model.like.PostLike;
import com.forum.application.model.like.ReplyLike;
import com.forum.application.model.mention.CommentMention;
import com.forum.application.model.mention.PostMention;
import com.forum.application.model.mention.ReplyMention;
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
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address", unique = true)
    private String email;

    @Column(name = "picture")
    private String picture;

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

    // user id reference is in tbl liked post
    @OneToMany(mappedBy = "respondent")
    @Setter(AccessLevel.NONE)
    private Set<PostLike> likedPosts;

    // user id reference is in tbl liked comment
    @OneToMany(mappedBy = "respondent")
    @Setter(AccessLevel.NONE)
    private Set<CommentLike> likedComments;

    // user id reference is in tbl liked reply
    @OneToMany(mappedBy = "respondent")
    @Setter(AccessLevel.NONE)
    private Set<ReplyLike> likedReplies;

    // user id reference is in post table
    @OneToMany(mappedBy = "author")
    @Setter(AccessLevel.NONE)
    private List<Post> posts;

    // user id reference is in comment table
    @OneToMany(mappedBy = "commenter")
    @Setter(AccessLevel.NONE)
    private List<Comment> comments;

    // user id reference is in reply table
    @OneToMany(mappedBy = "replier")
    @Setter(AccessLevel.NONE)
    private List<Reply> replies;

    // user id reference is in tbl mention post
    @OneToMany(mappedBy = "mentioningUser")
    @Setter(AccessLevel.NONE)
    private Set<PostMention> sentPostMentions;

    // user id reference is in tbl mention post
    @OneToMany(mappedBy = "mentionedUser")
    @Setter(AccessLevel.NONE)
    private Set<PostMention> receivePostMentions;

    // user id reference is in tbl mention comment
    @OneToMany(mappedBy = "mentioningUser")
    @Setter(AccessLevel.NONE)
    private Set<CommentMention> sentCommentMentions;

    // user id reference is in tbl mention comment
    @OneToMany(mappedBy = "mentionedUser")
    @Setter(AccessLevel.NONE)
    private Set<CommentMention> receiveCommentMentions;

    // user id reference is in tbl mention reply
    @OneToMany(mappedBy = "mentioningUser")
    @Setter(AccessLevel.NONE)
    private Set<ReplyMention> sentReplyMentions;

    // user id reference is in tbl mention reply
    @OneToMany(mappedBy = "mentionedUser")
    @Setter(AccessLevel.NONE)
    private Set<ReplyMention> receiveReplyMentions;
}

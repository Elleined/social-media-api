package com.elleined.socialmediaapi.model.main.post;

import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_post")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Post extends Forum {

    @ElementCollection
    @CollectionTable(
            name = "tbl_post_picture",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "attached_picture")
    private List<String> attachedPictures;

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
            name = "tbl_post_reaction",
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

    public enum CommentSectionStatus {OPEN, CLOSED}
}

package com.forum.application.model;

import com.forum.application.model.like.PostLike;
import com.forum.application.model.mention.PostMention;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_forum_post")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "attached_picture")
    private String attachedPicture;

    @Column(name = "comment_section_status")
    @Enumerated(EnumType.STRING)
    private CommentSectionStatus commentSectionStatus;

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "FK_author_id")
    )
    private User author;

    // post id reference is in comment table
    @OneToMany(mappedBy = "post")
    @Setter(AccessLevel.NONE)
    private List<Comment> comments;

    // post id refernce is on tbl mention post
    @OneToMany(mappedBy = "post")
    @Setter(AccessLevel.NONE)
    private Set<PostMention> mentions;

    // post id reference is in tbl post like
    @OneToMany(mappedBy = "post")
    @Setter(AccessLevel.NONE)
    private Set<PostLike> likes;


    public enum CommentSectionStatus {OPEN, CLOSED}
}

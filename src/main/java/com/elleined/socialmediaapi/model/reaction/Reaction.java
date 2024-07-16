package com.elleined.socialmediaapi.model.reaction;


import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Cacheable
@org.hibernate.annotations.Cache(region = "reactionCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_reaction")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Reaction extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "emoji_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Emoji emoji;

    @ManyToMany(mappedBy = "reactions")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "reactions")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "reactions")
    private Set<Reply> replies;

    @ManyToMany(mappedBy = "reactions")
    private Set<Story> stories;

    @ManyToMany(mappedBy = "reactions")
    private Set<Note> notes;
}

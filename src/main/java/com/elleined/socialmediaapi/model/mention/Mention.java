package com.elleined.socialmediaapi.model.mention;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_mention")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Mention extends PrimaryKeyIdentity {

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
            name = "mentioned_user_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User mentionedUser;

    @ManyToMany(mappedBy = "mentions")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "mentions")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "mentions")
    private Set<Reply> replies;

    @ManyToMany(mappedBy = "mentions")
    private Set<Story> stories;

    public Set<Integer> getAllPostIds() {
        return this.getPosts().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllCommentIds() {
        return this.getComments().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllReplyIds() {
        return this.getReplies().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }

    public Set<Integer> getAllStoryIds() {
        return this.getStories().stream()
                .map(PrimaryKeyIdentity::getId)
                .collect(Collectors.toSet());
    }
}

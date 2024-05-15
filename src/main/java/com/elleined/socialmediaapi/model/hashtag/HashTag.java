package com.elleined.socialmediaapi.model.hashtag;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Reply;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_hashtag")
@NoArgsConstructor
@Getter
@Setter
public class HashTag extends PrimaryKeyIdentity {

    @Column(
            name = "keyword",
            nullable = false,
            updatable = false,
            unique = true,
            length = 25
    )
    private String keyword;

    @ManyToMany(mappedBy = "hashTags")
    private Set<Post> posts;

    @ManyToMany(mappedBy = "hashTags")
    private Set<Comment> comments;

    @ManyToMany(mappedBy = "hashTags")
    private Set<Reply> replies;

    @Builder
    public HashTag(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String keyword,
                   Set<Post> posts,
                   Set<Comment> comments,
                   Set<Reply> replies) {
        super(id, createdAt, updatedAt);
        this.keyword = keyword;
        this.posts = posts;
        this.comments = comments;
        this.replies = replies;
    }

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
}

package com.elleined.socialmediaapi.model.hashtag;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Builder
    public HashTag(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String keyword,
                   Set<Post> posts) {
        super(id, createdAt, updatedAt);
        this.keyword = keyword;
        this.posts = posts;
    }

    public Set<Integer> getAllPostIds() {
        return this.getPosts().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }
}

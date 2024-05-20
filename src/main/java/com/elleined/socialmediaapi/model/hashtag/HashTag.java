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
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_hashtag")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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

    public Set<Integer> getAllPostIds() {
        return this.getPosts().stream()
                .map(Forum::getId)
                .collect(Collectors.toSet());
    }
}

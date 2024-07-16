package com.elleined.socialmediaapi.model.hashtag;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Cacheable
@org.hibernate.annotations.Cache(region = "hastTagCache", usage = CacheConcurrencyStrategy.READ_WRITE)

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
}

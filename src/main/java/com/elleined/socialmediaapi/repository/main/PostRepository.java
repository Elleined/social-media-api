package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("""
            SELECT p
            FROM Post p
            JOIN p.hashTags hashtag
            WHERE hashtag.keyword
            LIKE CONCAT('%', :keyword, '%')
            """)
    Page<Post> getAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
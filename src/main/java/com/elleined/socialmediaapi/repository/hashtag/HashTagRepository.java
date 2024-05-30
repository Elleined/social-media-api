package com.elleined.socialmediaapi.repository.hashtag;

import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    @Query("""
            SELECT h.posts
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            """)
    Page<Post> getAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
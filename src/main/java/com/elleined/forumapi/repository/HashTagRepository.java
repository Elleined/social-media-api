package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    @Query("SELECT h.post FROM Hashtag h WHERE h.keyword LIKE CONCAT('%', :keyword, '%')")
    Set<Post> searchPostByHashtagKeyword(@Param("keyword") String keyword);
}
package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    @Query("""
            SELECT h
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            """)
    Set<HashTag> searchHashTagByKeyword(@Param("keyword") String keyword);

    @Query("""
            SELECT h.post
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            AND h.post.author <> :currentUser
            """)
    Set<Post> getAllPostByHashTagKeyword(@Param("currentUser") User currentUser,
                                         @Param("keyword") String keyword);
}
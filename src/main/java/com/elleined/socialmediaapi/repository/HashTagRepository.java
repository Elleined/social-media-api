package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.Reply;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

    @Query("""
            SELECT h.posts
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            """)
    Set<Post> getAllPostByHashTagKeyword(@Param("keyword") String keyword);

    @Query("""
            SELECT h.comments
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            """)
    Set<Comment> getAllCommentByHashTagKeyword(@Param("keyword") String keyword);

    @Query("""
            SELECT h.replies
            FROM HashTag h
            WHERE h.keyword LIKE CONCAT('%', :keyword, '%')
            """)
    Set<Reply> getAllReplyByHashTagKeyword(@Param("keyword") String keyword);
}
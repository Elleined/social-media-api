package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.react.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p.comments FROM Post p WHERE p = :post")
    Page<Comment> findAllComments(@Param("post") Post post, Pageable pageable);

    @Query("SELECT p.reactions FROM Post p WHERE p = :post")
    Page<Reaction> findAllReactions(@Param("post") Post post, Pageable pageable);
}
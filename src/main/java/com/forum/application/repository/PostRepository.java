package com.forum.application.repository;

import com.forum.application.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.author.id = :authorId")
    List<Post> fetchAllByAuthorId(@Param("authorId") int authorId);
}
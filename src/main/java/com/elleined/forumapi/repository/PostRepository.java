package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
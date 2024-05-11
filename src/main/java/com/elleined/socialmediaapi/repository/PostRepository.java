package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.main.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
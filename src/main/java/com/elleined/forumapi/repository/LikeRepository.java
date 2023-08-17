package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {
}
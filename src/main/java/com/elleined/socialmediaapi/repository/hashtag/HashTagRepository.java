package com.elleined.socialmediaapi.repository.hashtag;

import com.elleined.socialmediaapi.model.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
}
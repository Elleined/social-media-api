package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.mention.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
}
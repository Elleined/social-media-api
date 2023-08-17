package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.mention.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
}
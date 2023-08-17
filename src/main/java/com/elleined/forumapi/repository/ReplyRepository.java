package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
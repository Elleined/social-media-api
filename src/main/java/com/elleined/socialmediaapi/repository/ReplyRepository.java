package com.elleined.socialmediaapi.repository;

import com.elleined.socialmediaapi.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
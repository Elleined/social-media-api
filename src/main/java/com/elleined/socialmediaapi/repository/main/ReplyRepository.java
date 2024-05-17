package com.elleined.socialmediaapi.repository.main;

import com.elleined.socialmediaapi.model.main.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
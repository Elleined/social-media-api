package com.elleined.forumapi.repository;

import com.elleined.forumapi.model.react.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Integer> {
}
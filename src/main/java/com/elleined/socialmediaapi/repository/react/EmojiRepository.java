package com.elleined.socialmediaapi.repository.react;

import com.elleined.socialmediaapi.model.reaction.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiRepository extends JpaRepository<Emoji, Integer> {
}
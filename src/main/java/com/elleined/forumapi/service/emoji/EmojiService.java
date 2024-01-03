package com.elleined.forumapi.service.emoji;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.react.Emoji;

import java.util.List;

public interface EmojiService {

    List<Emoji> getAll();
    Emoji getById(int id) throws ResourceNotFoundException;
    Emoji getByType(Emoji.Type type);
}

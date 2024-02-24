package com.elleined.socialmediaapi.service.emoji;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.react.Emoji;

import java.util.List;

public interface EmojiService {

    List<Emoji> getAll();
    Emoji getById(int id) throws ResourceNotFoundException;
    Emoji getByType(Emoji.Type type);
}

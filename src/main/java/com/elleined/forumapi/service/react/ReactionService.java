package com.elleined.forumapi.service.react;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.React;

import java.util.List;

public interface ReactionService<ENTITY, REACT extends React> {
    List<REACT> getAll(ENTITY entity);
    List<REACT> getAllReactionByEmojiType(ENTITY entity, Emoji.Type type);
    REACT save(User currentUser, ENTITY entity, Emoji emoji);
    REACT update(User currentUser, REACT postReact, Emoji emoji);
    void delete(ENTITY entity, REACT postReact);
}

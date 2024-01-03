package com.elleined.forumapi.service.react;

import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.Emoji;
import com.elleined.forumapi.model.react.React;

import java.util.List;

public interface ReactionService<ENTITY, REACT extends React> {
    REACT getById(int id) throws ResourceNotFoundException;
    REACT getByUserReaction(User currentUser, ENTITY entity);
    List<REACT> getAll(ENTITY entity);
    List<REACT> getAllReactionByEmojiType(ENTITY entity, Emoji.Type type);
    REACT save(User currentUser, ENTITY entity, Emoji emoji);
    void update(User currentUser, ENTITY entity, REACT postReact, Emoji emoji);
    void delete(User currentUser, ENTITY entity, REACT postReact);
}

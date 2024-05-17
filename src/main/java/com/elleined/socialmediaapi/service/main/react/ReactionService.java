package com.elleined.socialmediaapi.service.main.react;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;

public interface ReactionService<ENTITY, REACT extends React> {
    REACT getById(int id) throws ResourceNotFoundException;
    REACT getByUserReaction(User currentUser, ENTITY entity);
    List<REACT> getAll(ENTITY entity);
    List<REACT> getAllReactionByEmojiType(ENTITY entity, Emoji.Type type);
    REACT save(User currentUser, ENTITY entity, Emoji emoji);
    void update(User currentUser, ENTITY entity, REACT postReact, Emoji emoji);
    void delete(User currentUser, REACT postReact);
}

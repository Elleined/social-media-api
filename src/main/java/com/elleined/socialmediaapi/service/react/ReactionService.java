package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;

import java.util.List;
import java.util.Set;

public interface ReactionService {
    React getById(int id) throws ResourceNotFoundException;
    List<React> getAllById(Set<Integer> ids);
    React save(User currentUser, Emoji emoji);
    void update(User currentUser, React react, Emoji emoji);
    void delete(User currentUser, React react);
}


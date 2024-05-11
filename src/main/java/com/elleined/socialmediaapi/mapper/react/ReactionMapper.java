package com.elleined.socialmediaapi.mapper.react;

import com.elleined.socialmediaapi.dto.ReactionDTO;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;

public interface ReactionMapper<ENTITY, T extends React> {

    ReactionDTO toDTO(T t);

    T toEntity(User currentUser, ENTITY entity, Emoji emoji);
}

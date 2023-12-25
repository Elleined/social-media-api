package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.React;

public interface ReactionMapper<ENTITY, T extends React> {

    ReactionDTO toDTO(T t);

    T toEntity(User currentUser, ENTITY entity, Emoji emoji);
}

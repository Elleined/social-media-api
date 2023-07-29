package com.forum.application.mapper;

import com.forum.application.dto.UserDTO;
import com.forum.application.model.User;
import com.forum.application.model.like.Like;
import com.forum.application.model.mention.Mention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    default UserDTO mapLikeToUserDTO(Like like) {
        return toDTO(like.getRespondent());
    }

    default UserDTO mapMentionToUser(Mention mention) {
        return toDTO(mention.getMentionedUser());
    }
}

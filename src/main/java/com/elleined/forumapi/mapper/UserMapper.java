package com.elleined.forumapi.mapper;

import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.Mention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    default UserDTO mapMentionToUser(Mention mention) {
        return toDTO(mention.getMentionedUser());
    }
}

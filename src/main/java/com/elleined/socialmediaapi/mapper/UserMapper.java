package com.elleined.socialmediaapi.mapper;

import com.elleined.socialmediaapi.dto.UserDTO;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.mention.Mention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);

    default UserDTO mapMentionToUser(Mention mention) {
        return toDTO(mention.getMentionedUser());
    }
}

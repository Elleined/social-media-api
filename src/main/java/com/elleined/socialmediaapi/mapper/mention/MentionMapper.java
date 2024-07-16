package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.dto.mention.MentionDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class
        }
)
public interface MentionMapper extends CustomMapper<Mention, MentionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "mentionedUserDTO", source = "mentionedUser")
    })
    MentionDTO toDTO(Mention mention);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "mentionedUser", expression = "java(mentionedUser)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "stories", expression = "java(new java.util.HashSet<>())")
    })
    Mention toEntity(User creator,
                     @Context User mentionedUser);
}

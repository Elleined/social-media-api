package com.elleined.socialmediaapi.mapper.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class
        }
)
public interface StoryMapper extends CustomMapper<Story, StoryDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "attachPicture", source = "attachPicture"),
            @Mapping(target = "creatorDTO", source = "creator")
    })
    StoryDTO toDTO(Story story);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "attachPicture", source = "attachPicture"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "mentions", source = "mentions"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
    })
    Story toEntity(User creator,
                   String content,
                   String attachPicture,
                   Set<Mention> mentions);
}

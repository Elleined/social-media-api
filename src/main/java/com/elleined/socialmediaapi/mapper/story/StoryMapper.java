package com.elleined.socialmediaapi.mapper.story;

import com.elleined.socialmediaapi.dto.story.StoryDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StoryMapper extends CustomMapper<Story, StoryDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "attachPicture", source = "attachPicture"),
            @Mapping(target = "creatorId", source = "creator.id"),
    })
    StoryDTO toDTO(Story story);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "content", expression = "java(content)"),
            @Mapping(target = "attachPicture", expression = "java(attachPicture)"),
            @Mapping(target = "creatorId", expression = "java(creator)"),
    })
    Story toEntity(User creator,
                   String content,
                   @Context String attachPicture);
}

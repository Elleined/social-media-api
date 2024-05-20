package com.elleined.socialmediaapi.mapper.emoji;

import com.elleined.socialmediaapi.dto.reaction.EmojiDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.react.Emoji;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmojiMapper extends CustomMapper<Emoji, EmojiDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "type", source = "type"),
    })
    EmojiDTO toDTO(Emoji emoji);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "type", expression = "java(type)"),
    })
    Emoji toEntity(String type);
}

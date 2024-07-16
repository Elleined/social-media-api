package com.elleined.socialmediaapi.mapper.hashtag;

import com.elleined.socialmediaapi.dto.hashtag.HashTagDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HashTagMapper extends CustomMapper<HashTag, HashTagDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "keyword", source = "keyword")
    })
    HashTagDTO toDTO(HashTag hashTag);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())")
    })
    HashTag toEntity(String keyword);
}

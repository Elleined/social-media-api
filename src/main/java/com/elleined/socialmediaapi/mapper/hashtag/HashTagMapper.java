package com.elleined.socialmediaapi.mapper.hashtag;

import com.elleined.socialmediaapi.dto.HashTagDTO;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface HashTagMapper {

    HashTagDTO toDTO(HashTag hashTag);

    @Mappings({
            @Mapping(target = "id", ignore = true),

            @Mapping(target = "keyword", expression = "java(keyword)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())")
    })
    HashTag toEntity(String keyword);
}

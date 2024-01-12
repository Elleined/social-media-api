package com.elleined.forumapi.mapper.hashtag;

import com.elleined.forumapi.dto.HashTagDTO;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface HashTagMapper {

    HashTagDTO toDTO(HashTag hashTag);

    @Mappings({
            @Mapping(target = "id", ignore = true),

            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "keyword", expression = "java(keyword)"),
            @Mapping(target = "post", expression = "java(post)"),
    })
    HashTag toEntity(String keyword, @Context Post post);
}

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

    @Mapping(target = "connectedPosts", expression = "java(hashTag.getPosts().stream().map(Post::getId).collect(Collectors.toSet()))")
    HashTagDTO toDTO(HashTag hashTag);

    @Mappings({
            @Mapping(target = "id", ignore = true),

            @Mapping(target = "keyword", expression = "java(keyword)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())")
    })
    HashTag toEntity(String keyword);
}

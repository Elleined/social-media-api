package com.elleined.forumapi.mapper.hashtag;

import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.hashtag.HashTag;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),

            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "keyword", expression = "java(keyword)"),
            @Mapping(target = "post", expression = "java(post)"),
    })
    HashTag toEntity(String keyword, @Context Post post);
}

package com.elleined.socialmediaapi.newmapper.hashtag;

import com.elleined.socialmediaapi.dto.hashtag.HashTagDTO;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.newmapper.CustomMapper;
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
            @Mapping(target = "keyword", source = "keyword"),
            @Mapping(target = "postIds", expression = "java(hashTag.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(hashTag.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(hashTag.getAllReplyIds())")
    })
    HashTagDTO toDTO(HashTag hashTag);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "keyword", expression = "java(keyword)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())")
    })
    HashTag toEntity(String keyword);
}

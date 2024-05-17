package com.elleined.socialmediaapi.mapper.react;

import com.elleined.socialmediaapi.dto.react.ReactDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReactMapper extends CustomMapper<React, ReactDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "emojiId", source = "emoji.id"),
            @Mapping(target = "postIds", expression = "java(react.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(react.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(react.getAllReplyIds())"),
            @Mapping(target = "notificationIds", expression = "java(react.getAllNotificationIds())")
    })
    ReactDTO toDTO(React react);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "emoji", expression = "java(emoji)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "notifications", expression = "java(new java.util.HashSet<>())")
    })
    React toEntity(User creator,
                   @Context Emoji emoji);
}

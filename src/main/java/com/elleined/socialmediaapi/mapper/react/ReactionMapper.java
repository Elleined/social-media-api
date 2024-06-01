package com.elleined.socialmediaapi.mapper.react;

import com.elleined.socialmediaapi.dto.reaction.ReactionDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReactionMapper extends CustomMapper<Reaction, ReactionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "emojiId", source = "emoji.id"),
            @Mapping(target = "postIds", expression = "java(reaction.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(reaction.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(reaction.getAllReplyIds())"),
            @Mapping(target = "storyIds", expression = "java(reaction.getAllStoryIds())"),
    })
    ReactionDTO toDTO(Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "emoji", expression = "java(emoji)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "stories", expression = "java(new java.util.HashSet<>())")
    })
    Reaction toEntity(User creator,
                      @Context Emoji emoji);
}

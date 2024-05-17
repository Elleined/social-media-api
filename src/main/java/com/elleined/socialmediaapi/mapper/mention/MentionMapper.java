package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.dto.mention.MentionDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {NotificationStatus.class})
public interface MentionMapper extends CustomMapper<Mention, MentionDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "mentionedUserId", source = "mentionedUser.id"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),
            @Mapping(target = "postIds", expression = "java(mention.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(mention.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(mention.getAllReplyIds())")
    })
    MentionDTO toDTO(Mention mention);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "mentionedUser", expression = "java(mentionedUser)"),
            @Mapping(target = "notificationStatus", expression = "java(NotificationStatus.UNREAD)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())")
    })
    Mention toEntity(User creator,
                     @Context User mentionedUser);
}

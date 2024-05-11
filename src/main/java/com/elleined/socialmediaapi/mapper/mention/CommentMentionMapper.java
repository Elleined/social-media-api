package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CommentMentionMapper extends MentionMapper<Comment> {
    @Override
    @Mappings({
            // Required
            @Mapping(target = "mentioningUser", expression = "java(mentioningUser)"),
            @Mapping(target = "mentionedUser", expression = "java(mentionedUser)"),
            @Mapping(target = "comment", expression = "java(comment)"),

            // Required auto fill
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)")
    })
    CommentMention toEntity(User mentioningUser,
                            @Context User mentionedUser,
                            @Context Comment comment,
                            @Context NotificationStatus notificationStatus);
}

package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReplyMentionMapper extends MentionMapper<Reply> {

    @Override
    @Mappings({
            // Required
            @Mapping(target = "mentioningUser", expression = "java(mentioningUser)"),
            @Mapping(target = "mentionedUser", expression = "java(mentionedUser)"),
            @Mapping(target = "reply", expression = "java(reply)"),

            // Required auto fill
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)")
    })
    ReplyMention toEntity(User mentioningUser,
                            @Context User mentionedUser,
                            @Context Reply reply,
                            @Context NotificationStatus notificationStatus);
}

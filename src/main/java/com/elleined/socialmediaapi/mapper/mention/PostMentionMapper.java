package com.elleined.socialmediaapi.mapper.mention;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.mention.PostMention;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMentionMapper extends MentionMapper<Post> {
    @Override
    @Mappings({
            // Required
            @Mapping(target = "mentioningUser", expression = "java(mentioningUser)"),
            @Mapping(target = "mentionedUser", expression = "java(mentionedUser)"),
            @Mapping(target = "post", expression = "java(post)"),

            // Required auto fill
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)")
    })
    PostMention toEntity(User mentioningUser,
                         @Context User mentionedUser,
                         @Context Post post,
                         @Context NotificationStatus notificationStatus);
}

package com.elleined.forumapi.mapper.notification.mention;

import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface PostMentionNotificationService extends MentionNotificationMapper<PostMention, PostNotification> {

        @Mappings({
            @Mapping(target = "id", source = "postMention.id"),
            @Mapping(target = "receiverId", expression = "java(postMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postMention.getMessage())"),
            @Mapping(target = "respondentId", source = "postMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "postMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "postMention.notificationStatus"),

            @Mapping(target = "postId", source = "postMention.post.id"),
    })
    PostNotification toNotification(PostMention postMention);
}

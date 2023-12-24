package com.elleined.forumapi.mapper.notification.mention;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface CommentMentionNotificationService extends MentionNotificationMapper<CommentMention, CommentNotification> {

    @Mappings({
            @Mapping(target = "id", source = "commentMention.id"),
            @Mapping(target = "receiverId", expression = "java(commentMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(commentMention.getMessage())"),
            @Mapping(target = "respondentId", source = "commentMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "commentMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "commentMention.notificationStatus"),

            @Mapping(target = "postId", source = "commentMention.comment.post.id"),
            @Mapping(target = "commentId", source = "commentMention.comment.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    CommentNotification toNotification(CommentMention commentMention);
}

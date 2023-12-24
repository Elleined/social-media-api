package com.elleined.forumapi.mapper.notification.mention;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface ReplyMentionNotificationService extends MentionNotificationMapper<ReplyMention, ReplyNotification> {

    @Mappings({
            @Mapping(target = "id", source = "replyMention.id"),
            @Mapping(target = "receiverId", expression = "java(replyMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(replyMention.getMessage())"),
            @Mapping(target = "respondentId", source = "replyMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "replyMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "postId", source = "replyMention.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyMention.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyMention.reply.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    ReplyNotification toNotification(ReplyMention replyMention);
}

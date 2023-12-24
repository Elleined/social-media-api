package com.elleined.forumapi.mapper.notification.like;

import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface ReplyLikeNotificationMapper extends LikeNotificationMapper<ReplyLike, ReplyNotification> {
    @Override
    @Mappings(value = {
            @Mapping(target = "id", source = "replyLike.id"),
            @Mapping(target = "receiverId", expression = "java(replyLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(replyLike.getMessage())"),
            @Mapping(target = "respondentId", source = "replyLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "replyLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "replyLike.notificationStatus"),

            @Mapping(target = "postId", source = "replyLike.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyLike.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyLike.reply.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    ReplyNotification toNotification(ReplyLike replyLike);
}

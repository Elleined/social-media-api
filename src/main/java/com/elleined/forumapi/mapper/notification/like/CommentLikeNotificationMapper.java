package com.elleined.forumapi.mapper.notification.like;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface CommentLikeNotificationMapper extends LikeNotificationMapper<CommentLike, CommentNotification> {

    @Override
    @Mappings(value = {
            @Mapping(target = "id", source = "commentLike.id"),
            @Mapping(target = "receiverId", expression = "java(commentLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(commentLike.getMessage())"),
            @Mapping(target = "respondentId", source = "commentLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "commentLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "commentLike.notificationStatus"),

            @Mapping(target = "postId", source = "commentLike.comment.post.id"),
            @Mapping(target = "commentId", source = "commentLike.comment.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    CommentNotification toNotification(CommentLike commentLike);
}

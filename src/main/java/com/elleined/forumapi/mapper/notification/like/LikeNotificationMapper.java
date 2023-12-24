package com.elleined.forumapi.mapper.notification.like;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface LikeNotificationMapper {

    @Mappings(value = {
            @Mapping(target = "id", source = "postLike.id"),
            @Mapping(target = "receiverId", expression = "java(postLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postLike.getMessage())"),
            @Mapping(target = "respondentId", source = "postLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "postLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "postLike.notificationStatus"),

            @Mapping(target = "postId", source = "postLike.post.id"),
    })
    PostNotification toNotification(PostLike postLike);

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

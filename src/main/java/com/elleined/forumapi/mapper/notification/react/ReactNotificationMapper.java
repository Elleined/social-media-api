package com.elleined.forumapi.mapper.notification.react;

import com.elleined.forumapi.dto.notification.ReactionNotification;
import com.elleined.forumapi.model.react.CommentReact;
import com.elleined.forumapi.model.react.PostReact;
import com.elleined.forumapi.model.react.ReplyReact;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = Formatter.class)
public interface ReactNotificationMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "receiverId", expression = "java(postReact.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postReact.getMessage())"),
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "respondentPicture", source = "respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postReact.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postReact.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "emojiType", source = "")
    })
    ReactionNotification toNotification(PostReact postReact);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "receiverId", expression = "java(commentReact.getReceiverId())"),
            @Mapping(target = "message", expression = "java(commentReact.getMessage())"),
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "respondentPicture", source = "respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentReact.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentReact.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "emojiType", source = "")
    })
    ReactionNotification toNotification(CommentReact commentReact);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "receiverId", expression = "java(replyReact.getReceiverId())"),
            @Mapping(target = "message", expression = "java(replyReact.getMessage())"),
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "respondentPicture", source = "respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyReact.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyReact.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "emojiType", source = "")
    })
    ReactionNotification toNotification(ReplyReact replyReact);
}

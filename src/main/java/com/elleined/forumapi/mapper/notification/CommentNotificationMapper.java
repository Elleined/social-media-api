package com.elleined.forumapi.mapper.notification;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface CommentNotificationMapper extends NotificationMapper<Comment, CommentNotification> {

    @Override
    @Mappings(value = {
            @Mapping(target = "id", source = "comment.id"),
            @Mapping(target = "receiverId", source = "comment.commenter.id"),
            @Mapping(target = "message", expression = "java(getMessage(comment))"),
            @Mapping(target = "respondentPicture", source = "comment.commenter.picture"),
            @Mapping(target = "respondentId", source = "comment.commenter.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(comment.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(comment.getDateCreated()))"),
            @Mapping(target = "notificationStatus", source = "comment.notificationStatus"),

            @Mapping(target = "postId", source = "comment.post.id"),
            @Mapping(target = "commentId", source = "comment.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(comment))"),
    })
    CommentNotification toNotification(Comment comment);

    default int getNotificationCount(Comment comment) {
        return commentNotificationService.notificationCountForCommenter(comment.getPost().getAuthor(), comment.getPost(), comment.getCommenter());
    }

    default String getMessage(Comment comment) {
        return comment.getCommenter().getName() + " commented in your post: " + "\"" + comment.getPost().getBody() + "\"";
    }
}

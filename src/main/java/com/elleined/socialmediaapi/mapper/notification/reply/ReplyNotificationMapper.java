package com.elleined.socialmediaapi.mapper.notification.reply;

import com.elleined.socialmediaapi.dto.notification.ReplyNotification;
import com.elleined.socialmediaapi.mapper.notification.NotificationMapper;
import com.elleined.socialmediaapi.model.Reply;
import com.elleined.socialmediaapi.service.Formatter;
import com.elleined.socialmediaapi.service.notification.reply.ReplyNotificationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", imports = Formatter.class)
public abstract class ReplyNotificationMapper implements NotificationMapper<Reply, ReplyNotification> {

    @Autowired
    @Lazy
    private ReplyNotificationService replyNotificationService;

    @Override
    @Mappings(value = {
            @Mapping(target = "id", source = "reply.id"),
            @Mapping(target = "receiverId", source = "reply.replier.id"),
            @Mapping(target = "message", expression = "java(getMessage(reply))"),
            @Mapping(target = "respondentPicture", source = "reply.replier.picture"),
            @Mapping(target = "respondentId", source = "reply.replier.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(reply.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(reply.getDateCreated()))"),
            @Mapping(target = "notificationStatus", source = "reply.notificationStatus"),

            @Mapping(target = "postId", source = "reply.comment.post.id"),
            @Mapping(target = "commentId", source = "reply.comment.id"),
            @Mapping(target = "replyId", source = "reply.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(reply))"),
    })
    public abstract ReplyNotification toNotification(Reply reply);

    protected String getMessage(Reply reply) {
        return reply.getReplier().getName() + " replied to your comment: " + "\"" + reply.getComment().getBody() + "\"";
    }

    protected int getNotificationCount(Reply reply) {
        return replyNotificationService.notificationCountForReplier(reply.getComment().getCommenter(), reply.getComment(), reply.getReplier());
    }
}

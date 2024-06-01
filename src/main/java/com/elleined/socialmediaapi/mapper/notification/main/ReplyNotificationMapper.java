package com.elleined.socialmediaapi.mapper.notification.main;

import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface ReplyNotificationMapper extends CustomMapper<ReplyNotification, ReplyNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "replyId", source = "reply.id"),
    })
    ReplyNotificationDTO toDTO(ReplyNotification replyNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(reply.getComment().getCreator())"),
            @Mapping(target = "reply", expression = "java(reply)"),
    })
    ReplyNotification toEntity(User creator,
                                 @Context Reply reply);
}

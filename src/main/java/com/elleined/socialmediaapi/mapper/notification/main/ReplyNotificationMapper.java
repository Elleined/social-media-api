package com.elleined.socialmediaapi.mapper.notification.main;

import com.elleined.socialmediaapi.dto.notification.main.ReplyNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {
                Notification.Status.class
        },
        uses = {
                UserMapper.class,
                ReplyMapper.class
        }
)
public interface ReplyNotificationMapper extends CustomMapper<ReplyNotification, ReplyNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "replyDTO", source = "reply"),
    })
    ReplyNotificationDTO toDTO(ReplyNotification replyNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "reply.comment.creator"),
            @Mapping(target = "reply", source = "reply"),
    })
    ReplyNotification toEntity(User creator,
                               Reply reply);
}

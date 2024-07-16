package com.elleined.socialmediaapi.mapper.notification.follow;

import com.elleined.socialmediaapi.dto.notification.follow.FollowerNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
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
                UserMapper.class
        }
)
public interface FollowerNotificationMapper extends CustomMapper<FollowerNotification, FollowerNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(followerNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver")
    })
    FollowerNotificationDTO toDTO(FollowerNotification followerNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "receiver")
    })
    FollowerNotification toEntity(User creator,
                                  User receiver);
}

package com.elleined.socialmediaapi.mapper.notification.follow;

import com.elleined.socialmediaapi.dto.notification.follow.FollowerNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.follow.FollowerNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface FollowNotificationMapper extends CustomMapper<FollowerNotification, FollowerNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(followerNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
    })
    FollowerNotificationDTO toDTO(FollowerNotification followerNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(receiver)"),
    })
    FollowerNotification toEntity(User creator,
                                  @Context User receiver);
}

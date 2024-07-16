package com.elleined.socialmediaapi.mapper.notification.friend;

import com.elleined.socialmediaapi.dto.notification.friend.FriendRequestNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.friend.FriendRequestMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.friend.FriendRequestNotification;
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
                FriendRequestMapper.class
        }
)
public interface FriendRequestNotificationMapper extends CustomMapper<FriendRequestNotification, FriendRequestNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "friendRequestDTO", source = "friendRequest"),
            @Mapping(target = "message", source = "message"),
    })
    FriendRequestNotificationDTO toDTO(FriendRequestNotification friendRequestNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "receiver"),
            @Mapping(target = "friendRequest", source = "friendRequest"),
            @Mapping(target = "message", source = "message"),
    })
    FriendRequestNotification toEntity(User creator,
                                       User receiver,
                                       String message,
                                       FriendRequest friendRequest);
}

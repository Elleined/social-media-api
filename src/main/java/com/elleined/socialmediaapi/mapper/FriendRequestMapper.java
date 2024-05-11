package com.elleined.socialmediaapi.mapper;

import com.elleined.socialmediaapi.dto.FriendRequestDTO;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = UserMapper.class, imports = NotificationStatus.class)
public interface FriendRequestMapper {

    FriendRequestDTO toDTO(FriendRequest friendRequest);

    @Mappings({
            @Mapping(target = "id", ignore = true),

            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "requestingUser", expression = "java(requestingUser)"),
            @Mapping(target = "requestedUser", expression = "java(requestedUser)"),
            @Mapping(target = "notificationStatus", expression = "java(NotificationStatus.UNREAD)")
    })
    FriendRequest toEntity(User requestingUser,
                           @Context User requestedUser);
}

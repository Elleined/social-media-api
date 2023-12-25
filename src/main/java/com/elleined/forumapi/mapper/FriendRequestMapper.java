package com.elleined.forumapi.mapper;

import com.elleined.forumapi.dto.friend.FriendRequestDTO;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.friend.FriendRequest;
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

package com.elleined.socialmediaapi.mapper.friend;

import com.elleined.socialmediaapi.dto.friend.FriendRequestDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper extends CustomMapper<FriendRequest, FriendRequestDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "requestedUserId", source = "requestedUser.id")
    })
    FriendRequestDTO toDTO(FriendRequest friendRequest);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "requestedUser", expression = "java(requestedUser)")
    })
    FriendRequest toEntity(User creator,
                           @Context User requestedUser);
}


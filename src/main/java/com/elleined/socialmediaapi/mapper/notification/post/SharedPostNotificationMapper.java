package com.elleined.socialmediaapi.mapper.notification.post;

import com.elleined.socialmediaapi.dto.notification.post.SharedPostNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.post.SharedPostNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface SharedPostNotificationMapper extends CustomMapper<SharedPostNotification, SharedPostNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(sharedPostNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "post.creator.id"),
            @Mapping(target = "sharedPostId", source = "post.id")
    })
    SharedPostNotificationDTO toDTO(SharedPostNotification sharedPostNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(post.getCreator())"),
            @Mapping(target = "post", expression = "java(post)"),
    })
    SharedPostNotification toEntity(User creator,
                                    @Context Post post);
}

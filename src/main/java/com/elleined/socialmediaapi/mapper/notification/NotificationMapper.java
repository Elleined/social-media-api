package com.elleined.socialmediaapi.mapper.notification;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface NotificationMapper extends CustomMapper<Notification, NotificationDTO>  {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "senderId", source = "sender.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "message", source = "message"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "postIds", expression = "java(notification.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(notification.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(notification.getAllReplyIds())"),
            @Mapping(target = "reactIds", expression = "java(notification.getAllReactionIds())"),
            @Mapping(target = "mentionIds", expression = "java(notification.getAllMentionIds())"),
            @Mapping(target = "friendRequestIds", expression = "java(notification.getAllFriendRequestIds())"),
    })
    NotificationDTO toDTO(Notification notification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "receiver", expression = "java(receiver)"),
            @Mapping(target = "sender", expression = "java(sender)"),
            @Mapping(target = "message", expression = "java(message)"),
            @Mapping(target = "status", expression = "java(Status.UNREAD)"),
            @Mapping(target = "posts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "mentions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "friendRequests", expression = "java(new java.util.HashSet<>())"),
    })
    Notification toEntity(User sender,
                          User receiver,
                          @Context String message);
}

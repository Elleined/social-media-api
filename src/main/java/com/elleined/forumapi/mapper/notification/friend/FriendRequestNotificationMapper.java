package com.elleined.forumapi.mapper.notification.friend;

import com.elleined.forumapi.dto.notification.FriendRequestNotification;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {NotificationStatus.class, Formatter.class})
public interface FriendRequestNotificationMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "receiverId", source = "requestedUser.id"),
            @Mapping(target = "message", expression = "java(getSendMessage(friendRequest))"),
            @Mapping(target = "respondentPicture", source = "requestingUser.picture"),
            @Mapping(target = "respondentId", source = "requestingUser.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(friendRequest.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(friendRequest.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "friendRequest.notificationStatus"),
    })
    FriendRequestNotification toSendNotification(FriendRequest friendRequest);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "receiverId", source = "requestingUser.id"),
            @Mapping(target = "message", expression = "java(getAcceptedMessage(friendRequest))"),
            @Mapping(target = "respondentPicture", source = "requestedUser.picture"),
            @Mapping(target = "respondentId", source = "requestedUser.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(friendRequest.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(friendRequest.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "friendRequest.notificationStatus"),
    })
    FriendRequestNotification toAcceptedNotification(FriendRequest friendRequest);

    default String getSendMessage(FriendRequest friendRequest) {
        return friendRequest.getRequestingUser().getName() + " sent you a friend request";
        // requestedUser.name accepted your friend request
    }

    default String getAcceptedMessage(FriendRequest friendRequest) {
        return friendRequest.getRequestedUser().getName() + " accepted your friend request";
    }
}

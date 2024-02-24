package com.elleined.socialmediaapi.mapper.notification.friend;

import com.elleined.socialmediaapi.dto.notification.FriendRequestNotification;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.service.Formatter;
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

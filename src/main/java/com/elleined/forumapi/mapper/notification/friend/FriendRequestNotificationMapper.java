package com.elleined.forumapi.mapper.notification.friend;

import com.elleined.forumapi.dto.notification.FriendRequestNotification;
import com.elleined.forumapi.mapper.notification.NotificationMapper;
import com.elleined.forumapi.model.friend.FriendRequest;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface FriendRequestNotificationMapper extends NotificationMapper<FriendRequest, FriendRequestNotification> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "comment.id"),
            @Mapping(target = "receiverId", source = "comment.commenter.id"),
            @Mapping(target = "message", expression = "java(getMessage(friendRequest))"),
            @Mapping(target = "respondentPicture", source = "comment.commenter.picture"),
            @Mapping(target = "respondentId", source = "comment.commenter.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(comment.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(comment.getDateCreated()))"),
            @Mapping(target = "notificationStatus", source = "comment.notificationStatus"),
    })
    FriendRequestNotification toNotification(FriendRequest friendRequest);

    default String getMessage(FriendRequest friendRequest) {
        return "Friend Request Message";
    }
}

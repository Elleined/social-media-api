package com.elleined.forumapi.mapper.notification.like;

import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class)
public interface PostLikeNotificationMapper extends LikeNotificationMapper<PostLike, PostNotification> {
    @Override
    @Mappings(value = {
            @Mapping(target = "id", source = "postLike.id"),
            @Mapping(target = "receiverId", expression = "java(postLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postLike.getMessage())"),
            @Mapping(target = "respondentId", source = "postLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "postLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "postLike.notificationStatus"),
            @Mapping(target = "postId", source = "postLike.post.id"),
    })
    PostNotification toNotification(PostLike postLike);
}


package com.elleined.socialmediaapi.mapper;

import com.elleined.socialmediaapi.dto.ReplyDTO;
import com.elleined.socialmediaapi.model.*;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.Formatter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {
        Formatter.class,
        Status.class
}, uses = UserMapper.class)
public abstract class ReplyMapper {

    @Mappings({
            // Should not be touched!
            @Mapping(target = "id", ignore = true),

            // Required
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "replier", expression = "java(currentUser)"),
            @Mapping(target = "comment", expression = "java(comment)"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)"),

            // Required auto fill
            @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),

            // Required list
            @Mapping(target = "mentions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "hashTags", expression = "java(new java.util.HashSet<>())"),

            // Optional
            @Mapping(target = "attachedPicture", expression = "java(picture)"),
    })
    public abstract Reply toEntity(String body,
                                   @Context User currentUser,
                                   @Context Comment comment,
                                   @Context String picture,
                                   @Context NotificationStatus notificationStatus);

    @Mappings({
            @Mapping(target = "replierName", source = "reply.replier.name"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDateWithoutYear(reply.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(reply.getDateCreated()))"),
            @Mapping(target = "commentId", source = "reply.comment.id"),
            @Mapping(target = "replierId", source = "replier.id"),
            @Mapping(target = "replierPicture", source = "replier.picture"),
            @Mapping(target = "status", source = "reply.status"),
            @Mapping(target = "postId", source = "reply.comment.post.id"),
            @Mapping(target = "notificationStatus", source = "reply.notificationStatus"),
            @Mapping(target = "mentionedUsers", source = "reply.mentions"),
            @Mapping(target = "commenterId", source = "reply.comment.commenter.id"),
            @Mapping(target = "commentBody", source = "reply.comment.body")
    })
    public abstract ReplyDTO toDTO(Reply reply);
}

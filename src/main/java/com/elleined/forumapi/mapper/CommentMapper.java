package com.elleined.forumapi.mapper;

import com.elleined.forumapi.dto.CommentDTO;
import com.elleined.forumapi.model.*;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
@Mapper(componentModel = "spring", imports = {
        Formatter.class,
        Status.class
}, uses = UserMapper.class)
public abstract class CommentMapper {

    @Autowired @Lazy
    protected CommentService commentService;

    @Mappings({
            // Should not be touched!
            @Mapping(target = "id", ignore = true),

            // Required
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "post", expression = "java(post)"),
            @Mapping(target = "commenter", expression = "java(currentUser)"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)"),

            // Required auto fill
            @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),

            // Required list
            @Mapping(target = "mentions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "upvotingUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "hashTags", expression = "java(new java.util.HashSet<>())"),

            // Optional
            @Mapping(target = "attachedPicture", expression = "java(picture)"),
            @Mapping(target = "pinnedReply", expression = "java(null)"),
    })
    public abstract Comment toEntity(String body,
                                     @Context Post post,
                                     @Context User currentUser,
                                     @Context String picture,
                                     @Context NotificationStatus notificationStatus);

    @Mappings({
            @Mapping(target = "commenterName", source = "comment.commenter.name"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDateWithoutYear(comment.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(comment.getDateCreated()))"),
            @Mapping(target = "postId", source = "comment.post.id"),
            @Mapping(target = "commenterId", source = "comment.commenter.id"),
            @Mapping(target = "commenterPicture", source = "comment.commenter.picture"),
            @Mapping(target = "authorName", source = "comment.post.author.name"),
            @Mapping(target = "status", source = "comment.status"),
            @Mapping(target = "totalReplies", expression = "java(commentService.getTotalReplies(comment))"),
            @Mapping(target = "notificationStatus", source = "comment.notificationStatus"),
            @Mapping(target = "postBody", source = "comment.post.body"),
            @Mapping(target = "mentionedUsers", source = "comment.mentions"),
            @Mapping(target = "authorId", source = "comment.post.author.id"),
            @Mapping(target = "pinnedReplyId", source = "comment.pinnedReply.id"),
    })
    public abstract CommentDTO toDTO(Comment comment);
}

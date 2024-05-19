package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Forum.Status.class})
public interface CommentMapper extends CustomMapper<Comment, CommentDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPicture", source = "attachedPicture"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "notificationIds", expression = "java(comment.getAllNotificationIds())"),
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "pinnedReplyId", source = "pinnedReply.id"),
            @Mapping(target = "mentionIds", expression = "java(comment.getAllMentionIds())"),
            @Mapping(target = "reactionIds", expression = "java(comment.getAllReactionIds())"),
            @Mapping(target = "replyIds", expression = "java(comment.getAllReplyIds())"),
            @Mapping(target = "userVoteIds", expression = "java(comment.getAllVotersIds())"),
    })
    CommentDTO toDTO(Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPicture", expression = "java(attachedPicture)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "notifications", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "post", expression = "java(post)"),
            @Mapping(target = "pinnedReply", expression = "java(null)"),
            @Mapping(target = "mentions", expression = "java(mentions)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "voters", expression = "java(new java.util.ArrayList<>())")
    })
    Comment toEntity(User creator,
                     Post post,
                     @Context String body,
                     String attachedPicture,
                     Set<Mention> mentions);
}

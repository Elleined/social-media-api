package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Status;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Status.class})
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
            @Mapping(target = "notificationStatus", source = "notificationStatus"),
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "pinnedReplyId", source = "pinnedReply.id"),
            @Mapping(target = "hashTagIds", expression = "java(comment.getAllHashTagIds())"),
            @Mapping(target = "mentionIds", expression = "java(comment.getAllMentionIds())"),
            @Mapping(target = "reactionIds", expression = "java(comment.getAllReactionIds())"),
            @Mapping(target = "replyIds", expression = "java(comment.getAllReplyIds())"),
            @Mapping(target = "userVoteIds", expression = "java(comment.getAllUserVoteIds())"),
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
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)"),
            @Mapping(target = "post", expression = "java(post)"),
            @Mapping(target = "pinnedReply", expression = "java(null)"),
            @Mapping(target = "hashTags", expression = "java(hashTags)"),
            @Mapping(target = "mentions", expression = "java(mentions)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "userVotes", expression = "java(new java.util.HashSet<>())")
    })
    Comment toEntity(User creator,
                     Post post,
                     String body,
                     String attachedPicture,
                     Set<HashTag> hashTags,
                     Set<Mention> mentions,
                     @Context NotificationStatus notificationStatus);
}

package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Forum.Status.class})
public interface ReplyMapper extends CustomMapper<Reply, ReplyDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPicture", source = "attachedPicture"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "notificationIds", expression = "java(reply.getAllNotificationIds())"),
            @Mapping(target = "commentId", source = "comment.id"),
            @Mapping(target = "hashTagIds", expression = "java(reply.getAllHashTagIds())"),
            @Mapping(target = "mentionIds", expression = "java(reply.getAllMentionIds())"),
            @Mapping(target = "reactionIds", expression = "java(reply.getAllReactionIds())"),
    })
    ReplyDTO toDTO(Reply reply);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPicture", expression = "java(attachedPicture)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "notifications", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comment", expression = "java(comment)"),
            @Mapping(target = "hashTags", expression = "java(hashTags)"),
            @Mapping(target = "mentions", expression = "java(mentions)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
    })
    Reply toEntity(User creator,
                   Comment comment,
                   @Context String body,
                   String attachedPicture,
                   Set<Mention> mentions,
                   Set<HashTag> hashTags);
}

package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
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
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "pinnedReplyId", source = "pinnedReply.id"),
            @Mapping(target = "hashTagIds", expression = "java(comment.getAllHashTagIds())"),
            @Mapping(target = "mentionIds", expression = "java(comment.getAllMentionIds())"),
            @Mapping(target = "reactionIds", expression = "java(comment.getAllReactionIds())"),
            @Mapping(target = "replyIds", expression = "java(comment.getAllReplyIds())"),
            @Mapping(target = "voteIds", expression = "java(comment.getAllVoteIds())"),
    })
    CommentDTO toDTO(Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPictures", expression = "java(attachedPictures)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "post", expression = "java(post)"),
            @Mapping(target = "pinnedReply", expression = "java(null)"),
            @Mapping(target = "hashTags", expression = "java(hashTags)"),
            @Mapping(target = "mentions", expression = "java(mentions)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "votes", expression = "java(new java.util.ArrayList<>())")
    })
    Comment toEntity(User creator,
                     Post post,
                     @Context String body,
                     List<String> attachedPictures,
                     Set<Mention> mentions,
                     Set<HashTag> hashTags);
}

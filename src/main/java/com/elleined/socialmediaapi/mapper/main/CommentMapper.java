package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        imports = {
                Forum.Status.class
        },
        uses = {
                UserMapper.class,
                PostMapper.class,
                ReplyMapper.class
        }
)
public interface CommentMapper extends CustomMapper<Comment, CommentDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "postDTO", source = "post"),
            @Mapping(target = "pinnedReplyDTO", source = "pinnedReply")
    })
    CommentDTO toDTO(Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "post", source = "post"),
            @Mapping(target = "pinnedReply", expression = "java(null)"),
            @Mapping(target = "hashTags", source = "hashTags"),
            @Mapping(target = "mentions", source = "mentions"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "votes", expression = "java(new java.util.ArrayList<>())")
    })
    Comment toEntity(User creator,
                     Post post,
                     String body,
                     List<String> attachedPictures,
                     Set<Mention> mentions,
                     Set<HashTag> hashTags);
}

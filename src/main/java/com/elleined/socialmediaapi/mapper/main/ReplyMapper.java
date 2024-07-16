package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
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
                CommentMapper.class
        }
)
public interface ReplyMapper extends CustomMapper<Reply, ReplyDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "postDTO", source = "reply.comment.post"), // this field is not present in entity
            @Mapping(target = "commentDTO", source = "comment")
    })
    ReplyDTO toDTO(Reply reply);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "comment", source = "comment"),
            @Mapping(target = "hashTags", source = "hashTags"),
            @Mapping(target = "mentions", source = "mentions"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
    })
    Reply toEntity(User creator,
                   Comment comment,
                   String body,
                   List<String> attachedPictures,
                   Set<Mention> mentions,
                   Set<HashTag> hashTags);
}

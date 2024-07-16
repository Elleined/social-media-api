package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
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
                Post.CommentSectionStatus.class,
                Forum.Status.class
        },
        uses = {
                UserMapper.class,
                CommentMapper.class
        }
)
public interface PostMapper extends CustomMapper<Post, PostDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "commentSectionStatus", source = "commentSectionStatus"),
            @Mapping(target = "pinnedCommentDTO", source = "pinnedComment")
    })
    PostDTO toDTO(Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPictures", source = "attachedPictures"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "commentSectionStatus", expression = "java(CommentSectionStatus.OPEN)"),
            @Mapping(target = "pinnedComment", expression = "java(null)"),
            @Mapping(target = "hashTags", source = "hashTags"),
            @Mapping(target = "mentions", source = "mentions"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "savingUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "sharers", expression = "java(new java.util.HashSet<>())")
    })
    Post toEntity(User creator,
                  String body,
                  List<String> attachedPictures,
                  Set<HashTag> hashTags,
                  Set<Mention> mentions);
}

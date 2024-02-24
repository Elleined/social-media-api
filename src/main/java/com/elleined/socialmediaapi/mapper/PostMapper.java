package com.elleined.socialmediaapi.mapper;


import com.elleined.socialmediaapi.dto.PostDTO;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.service.Formatter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {
        Formatter.class,
        Status.class,
        Post.CommentSectionStatus.class
}, uses = UserMapper.class)
public interface PostMapper {

    @Mappings({
            // Should not be touched!
            @Mapping(target = "id", ignore = true),

            // Required
            @Mapping(target = "author", expression = "java(currentUser)"),
            @Mapping(target = "body", expression = "java(body)"),

            // Required auto fill
            @Mapping(target = "dateCreated", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "commentSectionStatus", expression = "java(CommentSectionStatus.OPEN)"),

            // Required list
            @Mapping(target = "mentions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "savingUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "sharers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "hashTags", expression = "java(new java.util.HashSet<>())"),

            // Optional
            @Mapping(target = "attachedPicture", expression = "java(picture)"),
            @Mapping(target = "pinnedComment", expression = "java(null)"),
    })
    Post toEntity(String body,
                                  @Context User currentUser,
                                  @Context String picture);

    @Mappings({
            @Mapping(target = "formattedDateCreated", expression = "java(Formatter.formatDateWithoutYear(post.getDateCreated()))"),
            @Mapping(target = "formattedTimeCreated", expression = "java(Formatter.formatTime(post.getDateCreated()))"),
            @Mapping(target = "authorId", source = "post.author.id"),
            @Mapping(target = "authorName", source = "post.author.name"),
            @Mapping(target = "authorPicture", source = "post.author.picture"),
            @Mapping(target = "status", source = "post.status"),
            @Mapping(target = "commentSectionStatus", source = "post.commentSectionStatus"),
            @Mapping(target = "mentionedUsers", source = "post.mentions"),
            @Mapping(target = "attachedPicture", source = "post.attachedPicture"),
            @Mapping(target = "pinnedCommentId", source = "post.pinnedComment.id"),
    })
    PostDTO toDTO(Post post);
}

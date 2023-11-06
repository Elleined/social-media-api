package com.elleined.forumapi.mapper;


import com.elleined.forumapi.dto.PostDTO;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.service.Formatter;
import com.elleined.forumapi.service.post.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", imports = Formatter.class, uses = UserMapper.class)
public abstract class PostMapper {

    @Autowired @Lazy
    protected PostService postService;

    @Mappings({
            @Mapping(target = "formattedDateCreated", expression = "java(Formatter.formatDateWithoutYear(post.getDateCreated()))"),
            @Mapping(target = "formattedTimeCreated", expression = "java(Formatter.formatTime(post.getDateCreated()))"),
            @Mapping(target = "authorId", source = "post.author.id"),
            @Mapping(target = "authorName", source = "post.author.name"),
            @Mapping(target = "authorPicture", source = "post.author.picture"),
            @Mapping(target = "totalCommentAndReplies", expression = "java(postService.getTotalCommentsAndReplies(post))"),
            @Mapping(target = "status", source = "post.status"),
            @Mapping(target = "commentSectionStatus", source = "post.commentSectionStatus"),
            @Mapping(target = "likers", source = "post.likes"),
            @Mapping(target = "mentionedUsers", source = "post.mentions"),
            @Mapping(target = "totalLikes", expression = "java(post.getLikes().size())"),
            @Mapping(target = "attachedPicture", source = "post.attachedPicture"),
            @Mapping(target = "pinnedCommentId", source = "post.pinnedComment.id")
    })
    public abstract PostDTO toDTO(Post post);
}

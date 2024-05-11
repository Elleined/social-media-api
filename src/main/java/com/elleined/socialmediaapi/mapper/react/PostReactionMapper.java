package com.elleined.socialmediaapi.mapper.react;

import com.elleined.socialmediaapi.dto.ReactionDTO;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = NotificationStatus.class)
public interface PostReactionMapper extends ReactionMapper<Post, PostReact> {
    @Override
    @Mappings({
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "emojiType", source = "emoji.type"),
            @Mapping(target = "entityId", source = "post.id")
    })
    ReactionDTO toDTO(PostReact postReact);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "respondent", expression = "java(currentUser)"),
            @Mapping(target = "notificationStatus", expression = "java(NotificationStatus.UNREAD)"),
            @Mapping(target = "emoji", expression = "java(emoji)"),
            @Mapping(target = "post", expression = "java(post)"),
    })
    PostReact toEntity(User currentUser,
                       @Context Post post,
                       @Context Emoji emoji);
}

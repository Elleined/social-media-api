package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import com.elleined.forumapi.model.react.CommentReact;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring", imports = NotificationStatus.class)
public interface CommentReactionMapper extends ReactionMapper<Comment, CommentReact> {
    @Override
    @Mappings({
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "emojiType", source = "emoji.type"),
            @Mapping(target = "entityId", source = "comment.id")
    })
    ReactionDTO toDTO(CommentReact commentReact);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "respondent", expression = "java(currentUser)"),
            @Mapping(target = "notificationStatus", expression = "java(NotificationStatus.UNREAD)"),
            @Mapping(target = "emoji", expression = "java(emoji)"),
            @Mapping(target = "comment", expression = "java(comment)"),
    })
    CommentReact toEntity(User currentUser,
                          @Context Comment comment,
                          @Context Emoji emoji);
}

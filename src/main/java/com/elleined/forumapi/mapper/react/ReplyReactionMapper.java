package com.elleined.forumapi.mapper.react;

import com.elleined.forumapi.dto.ReactionDTO;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.Emoji;
import com.elleined.forumapi.model.react.ReplyReact;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = NotificationStatus.class)
public interface ReplyReactionMapper extends ReactionMapper<Reply, ReplyReact> {
    @Override
    @Mappings({
            @Mapping(target = "respondentId", source = "respondent.id"),
            @Mapping(target = "emojiType", source = "emoji.type"),
            @Mapping(target = "entityId", source = "reply.id")
    })
    ReactionDTO toDTO(ReplyReact replyReact);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "respondent", expression = "java(currentUser)"),
            @Mapping(target = "notificationStatus", expression = "java(NotificationStatus.UNREAD)"),
            @Mapping(target = "emoji", expression = "java(emoji)"),
            @Mapping(target = "reply", expression = "java(reply)"),
    })
    ReplyReact toEntity(User currentUser,
                        @Context Reply reply,
                        @Context Emoji emoji);
}

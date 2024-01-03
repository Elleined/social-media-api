
package com.elleined.forumapi.mapper;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.service.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = Formatter.class, uses = UserMapper.class)
public abstract class ReplyMapper {

    @Mappings({
            @Mapping(target = "replierName", source = "reply.replier.name"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDateWithoutYear(reply.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(reply.getDateCreated()))"),
            @Mapping(target = "commentId", source = "reply.comment.id"),
            @Mapping(target = "replierId", source = "replier.id"),
            @Mapping(target = "replierPicture", source = "replier.picture"),
            @Mapping(target = "status", source = "reply.status"),
            @Mapping(target = "postId", source = "reply.comment.post.id"),
            @Mapping(target = "notificationStatus", source = "reply.notificationStatus"),
            @Mapping(target = "mentionedUsers", source = "reply.mentions"),
            @Mapping(target = "commenterId", source = "reply.comment.commenter.id"),
            @Mapping(target = "commentBody", source = "reply.comment.body")
    })
    public abstract ReplyDTO toDTO(Reply reply);
}

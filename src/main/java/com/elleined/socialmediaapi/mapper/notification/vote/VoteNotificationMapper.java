package com.elleined.socialmediaapi.mapper.notification.vote;

import com.elleined.socialmediaapi.dto.notification.vote.VoteNotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.mapper.vote.VoteMapper;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.vote.VoteNotification;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {
                Notification.Status.class
        },
        uses = {
                UserMapper.class,
                VoteMapper.class
        }
)
public interface VoteNotificationMapper extends CustomMapper<VoteNotification, VoteNotificationDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(voteNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "vote.comment.creator"),
            @Mapping(target = "voteDTO", source = "vote"),
    })
    VoteNotificationDTO toDTO(VoteNotification voteNotification);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "vote.comment.creator"),
            @Mapping(target = "vote", source = "vote"),
    })
    VoteNotification toEntity(User creator,
                              Vote vote);
}

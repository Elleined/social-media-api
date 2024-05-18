package com.elleined.socialmediaapi.mapper.vote;

import com.elleined.socialmediaapi.dto.main.VoteDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VoteMapper extends CustomMapper<Vote, VoteDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "commentId", source = "comment.id"),
            @Mapping(target = "verdict", source = "verdict"),
    })
    VoteDTO toDTO(Vote vote);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "comment", expression = "java(comment)"),
            @Mapping(target = "verdict", expression = "java(verdict)"),
    })
    Vote toEntity(User creator,
                  Comment comment,
                  @Context Vote.Verdict verdict);
}

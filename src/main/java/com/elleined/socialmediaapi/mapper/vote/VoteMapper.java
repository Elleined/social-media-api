package com.elleined.socialmediaapi.mapper.vote;

import com.elleined.socialmediaapi.dto.vote.VoteDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class,
                CommentMapper.class
        }
)
public interface VoteMapper extends CustomMapper<Vote, VoteDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "commentDTO", source = "comment"),
            @Mapping(target = "verdict", source = "verdict"),
    })
    VoteDTO toDTO(Vote vote);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "comment", source = "comment"),
            @Mapping(target = "verdict", source = "verdict"),
    })
    Vote toEntity(User creator,
                  Comment comment,
                  Vote.Verdict verdict);
}

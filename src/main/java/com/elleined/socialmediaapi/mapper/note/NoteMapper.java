package com.elleined.socialmediaapi.mapper.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NoteMapper extends CustomMapper<Note, NoteDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "thought", source = "thought"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "reactionIds", expression = "java(note.getAllReactionIds())")
    })
    NoteDTO toDTO(Note note);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "thought", expression = "java(thought)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())")
    })
    Note toEntity(User creator,
                  @Context String thought);
}

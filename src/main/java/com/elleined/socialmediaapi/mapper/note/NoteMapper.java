package com.elleined.socialmediaapi.mapper.note;

import com.elleined.socialmediaapi.dto.NoteDTO;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.note.Note;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel =  "spring")
public interface NoteMapper {

    @Mappings({
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "thought", expression = "java(thought)"),
            @Mapping(target = "user", expression = "java(currentUser)")
    })
    Note toEntity(User currentUser, @Context String thought);

    @Mappings({
            @Mapping(target = "userId", expression = "java(note.getUser().getId())")
    })
    NoteDTO toDTO(Note note);
}

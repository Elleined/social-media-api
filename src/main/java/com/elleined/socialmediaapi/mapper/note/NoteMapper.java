package com.elleined.socialmediaapi.mapper.note;

import com.elleined.socialmediaapi.dto.note.NoteDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        uses = {
                UserMapper.class
        }
)
public interface NoteMapper extends CustomMapper<Note, NoteDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "thought", source = "thought"),
            @Mapping(target = "creatorDTO", source = "creator")
    })
    NoteDTO toDTO(Note note);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "thought", source = "thought"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())")
    })
    Note toEntity(User creator,
                  String thought);
}

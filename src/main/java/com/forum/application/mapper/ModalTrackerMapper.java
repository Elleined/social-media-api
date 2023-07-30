package com.forum.application.mapper;

import com.forum.application.dto.ModalTrackerDTO;
import com.forum.application.model.ModalTracker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModalTrackerMapper {

    @Mapping(target = "type", source = "modalTracker.type")
    ModalTrackerDTO toDTO(ModalTracker modalTracker);
}

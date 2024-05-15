package com.elleined.socialmediaapi.mapper;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;

public interface CustomMapper<ENTITY extends PrimaryKeyIdentity,
        DTO extends com.elleined.socialmediaapi.dto.DTO> {
    DTO toDTO(ENTITY entity);
}
package com.elleined.socialmediaapi.service;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomService<ENTITY extends PrimaryKeyIdentity> {
    ENTITY save(ENTITY entity);
    ENTITY getById(int id) throws ResourceNotFoundException;
    List<ENTITY> getAll(Pageable pageable);
    List<ENTITY> getAllById(List<Integer> ids);

    default List<ENTITY> saveAll(List<ENTITY> entities) {
        return entities.stream()
                .map(this::save)
                .toList();
    }
}

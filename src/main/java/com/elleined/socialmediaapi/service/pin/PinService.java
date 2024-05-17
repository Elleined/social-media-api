package com.elleined.socialmediaapi.service.pin;

import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.user.User;

public interface PinService<T, U> {
    void pin(User currentUser, T t, U u) throws ResourceNotOwnedException, ResourceNotFoundException;
    void unpin(T t);

    U getPinned(T t) throws ResourceNotFoundException;
}

package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.user.UserRequest;

import java.util.List;
import java.util.Set;

public interface UserService {
    User save(UserRequest userRequest);

    User getById(int userId) throws ResourceNotFoundException;

    List<User> getAllById(Set<Integer> ids);

    User getByUUID(String UUID) throws ResourceNotFoundException;

    boolean isEmailAlreadyExists(String email);
}

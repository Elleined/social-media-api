package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.request.user.UserRequest;
import com.elleined.socialmediaapi.service.CustomService;

public interface UserService extends CustomService<User> {
    User save(UserRequest userRequest);

    User getByUUID(String UUID) throws ResourceNotFoundException;

    boolean isEmailAlreadyExists(String email);
}

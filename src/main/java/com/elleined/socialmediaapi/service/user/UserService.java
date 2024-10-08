package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService extends CustomService<User> {
    Set<User> getAllById(Set<Integer> ids);

    User getByJWT(String jwt) throws ResourceNotFoundException;

    User register(String name,
                  String email,
                  String password,
                  String picture);

    String login(String email,
                 String password);

    User getByUUID(String UUID) throws ResourceNotFoundException;

    boolean isEmailAlreadyExists(String email);

    Page<User> getAllSuggestedMentions(User currentUser, String name, Pageable pageable);
}

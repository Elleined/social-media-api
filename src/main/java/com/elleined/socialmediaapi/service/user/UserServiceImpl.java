package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.field.EmailException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.request.user.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(UserRequest userRequest) {
        if (isEmailAlreadyExists(userRequest.getEmail())) throw new EmailException("Cannot save user! Because this email already exists!");
        User user = userMapper.toEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPicture());
        userRepository.save(user);
        log.debug("User with id of {} saved successfully!", user.getId());
        return user;
    }

    @Override
    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId +  " does not exists"));
    }

    @Override
    public List<User> getAllById(Set<Integer> ids) {
        return new HashSet<>(userRepository.findAllById(ids));
    }

    @Override
    public User getByUUID(String UUID) throws ResourceNotFoundException {
        return userRepository.findAll().stream()
                .filter(user -> user.getUUID().equals(UUID))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + UUID +  " does not exists"));
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(email::equalsIgnoreCase);
    }
}

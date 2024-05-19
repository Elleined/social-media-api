package com.elleined.socialmediaapi.service.user;

import com.elleined.socialmediaapi.exception.field.EmailException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.request.user.UserRequest;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.validator.EmailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService, UserServiceRestriction {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BlockService blockService;

    private final EmailValidator emailValidator;

    @Override
    public User save(UserRequest userRequest) {
        String email = userRequest.getEmail();
        if (isEmailAlreadyExists(email))
            throw new EmailException("Cannot save user! Because this email already exists!");
        emailValidator.validate(email);

        User user = userMapper.toEntity(userRequest.getName(), email, userRequest.getPicture());
        userRepository.save(user);
        log.debug("User with id of {} saved successfully!", user.getId());
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId +  " does not exists"));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .toList();
    }

    @Override
    public List<User> getAllById(List<Integer> ids) {
        return userRepository.findAllById(ids).stream()
                .sorted(Comparator.comparingInt(User::getId))
                .toList();
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


    @Override
    public List<User> getAllSuggestedMentions(User currentUser, String name) {
        return userRepository.fetchAllByProperty(name).stream()
                .filter(suggestedUser -> !suggestedUser.equals(currentUser))
                .filter(suggestedUser -> !blockService.isBlockedByYou(currentUser, suggestedUser))
                .filter(suggestedUser -> !blockService.isYouBeenBlockedBy(currentUser, suggestedUser))
                .sorted(Comparator.comparing(User::getName))
                .toList();
    }
}

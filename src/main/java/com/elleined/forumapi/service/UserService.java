package com.elleined.forumapi.service;

import com.elleined.forumapi.dto.UserDTO;
import com.elleined.forumapi.exception.ResourceNotFoundException;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;
    private final BlockService blockService;

    public User save(UserDTO userDTO) {
        User user = User.builder()
                .picture(userDTO.picture())
                .name(userDTO.name())
                .email(userDTO.email())
                .UUID(userDTO.UUID())
                .build();

        userRepository.save(user);
        log.debug("User with id of {} saved successfully!", userDTO.id());
        return user;
    }

    public User getById(int userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id of " + userId +  " does not exists"));
    }

    public Set<User> getAllById(Set<Integer> userIds) {
        return new HashSet<>(userRepository.findAllById(userIds));
    }

    public User getByUUID(String UUID) throws ResourceNotFoundException {
        return userRepository.findByUUID(UUID).orElseThrow(() -> new ResourceNotFoundException("User with UUID of " + UUID +  " does not exists"));
    }

    public List<User> getSuggestedMentions(User currentUser, String name) {
        return userRepository.fetchAllByProperty(name).stream()
                .filter(suggestedUser -> !suggestedUser.equals(currentUser))
                .filter(suggestedUser -> !blockService.isBlockedBy(currentUser, suggestedUser))
                .filter(suggestedUser -> !blockService.isYouBeenBlockedBy(currentUser, suggestedUser))
                .toList();
    }

}

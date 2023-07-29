package com.forum.application.service;

import com.forum.application.model.User;
import com.forum.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlockService {

    private final UserRepository userRepository;

    void blockUser(User currentUser, User userToBeBlocked) {
        currentUser.getBlockedUsers().add(userToBeBlocked);
        userRepository.save(currentUser);
        log.debug("User {} blocked User {} successfully", currentUser.getId(), userToBeBlocked.getId());
    }

    void unBlockUser(User currentUser, User userToBeUnblocked) {
        currentUser.getBlockedUsers().remove(userToBeUnblocked);
        userRepository.save(currentUser);
        log.debug("User {} unblocked user {} successfully", currentUser.getId(), userToBeUnblocked.getId());
    }

    public boolean isBlockedBy(User currentUser, User userToCheck) {
        return currentUser.getBlockedUsers().stream().anyMatch(userToCheck::equals);
    }

    public boolean isYouBeenBlockedBy(User currentUser, User suspectedUser) {
        return suspectedUser.getBlockedUsers().stream().anyMatch(currentUser::equals);
    }

    public Set<User> getAllBlockedUsers(User currentUser) {
        return currentUser.getBlockedUsers();
    }
}

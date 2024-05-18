package com.elleined.socialmediaapi.service.block;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BlockServiceImpl implements BlockService {
    private final UserRepository userRepository;

    @Override
    public void blockUser(User currentUser, User userToBeBlocked) throws BlockedException {
        if (currentUser.getId() == userToBeBlocked.getId())
            throw new BlockedException("Cannot block user! because you cannot blocked yourself!");

        if (isBlockedByYou(currentUser, userToBeBlocked))
            throw new BlockedException("Cannot block this user! because you already blocked this user!");

        if (isYouBeenBlockedBy(currentUser, userToBeBlocked))
            throw new BlockedException("Cannot block this user! because you've already been block by this user!");

        currentUser.getBlockedUsers().add(userToBeBlocked);
        userRepository.save(currentUser);
        log.debug("User {} blocked User {} successfully", currentUser.getId(), userToBeBlocked.getId());
    }

    @Override
    public void unBlockUser(User currentUser, User userToBeUnblocked) {
        if (!isBlockedByYou(currentUser, userToBeUnblocked))
            throw new BlockedException("Cannot unblock this user! because you are not blocked this user!");

        if (!isYouBeenBlockedBy(currentUser, userToBeUnblocked))
            throw new BlockedException("Cannot unblock this user! because you are not been block by this user!");

        currentUser.getBlockedUsers().remove(userToBeUnblocked);
        userRepository.save(currentUser);
        log.debug("User {} unblocked user {} successfully", currentUser.getId(), userToBeUnblocked.getId());
    }

    @Override
    public boolean isBlockedByYou(User currentUser, User userToCheck) {
        return currentUser.getBlockedUsers().stream().anyMatch(userToCheck::equals);
    }

    @Override
    public boolean isYouBeenBlockedBy(User currentUser, User suspectedUser) {
        return suspectedUser.getBlockedUsers().stream().anyMatch(currentUser::equals);
    }

    @Override
    public Set<User> getAllBlockedUsers(User currentUser) {
        return currentUser.getBlockedUsers();
    }
}

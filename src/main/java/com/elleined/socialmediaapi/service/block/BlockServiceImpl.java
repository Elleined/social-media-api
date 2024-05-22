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
@Service
@Transactional
@RequiredArgsConstructor
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
        userToBeBlocked.getBlockedUsers().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToBeBlocked);
        log.debug("User {} blocked User {} successfully", currentUser.getId(), userToBeBlocked.getId());
    }

    @Override
    public void unBlockUser(User currentUser, User userToBeUnblocked) {
        if (!isBlockedByYou(currentUser, userToBeUnblocked))
            throw new BlockedException("Cannot unblock this user! because you do not blocked this user!");

        if (!isYouBeenBlockedBy(currentUser, userToBeUnblocked))
            throw new BlockedException("Cannot block this user! because this user didn't block you!!");

        currentUser.getBlockedUsers().remove(userToBeUnblocked);
        userToBeUnblocked.getBlockedUsers().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToBeUnblocked);
        log.debug("User {} unblocked user {} successfully", currentUser.getId(), userToBeUnblocked.getId());
    }
}

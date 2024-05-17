package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final BlockService blockService;

    @Override
    public void follow(User currentUser, User userToFollow) {
        if (blockService.isBlockedBy(currentUser, userToFollow)) throw new BlockedException("Cannot follow because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, userToFollow)) throw new BlockedException("Cannot follow because this user block you already!");

        currentUser.getFollowings().add(userToFollow);
        userToFollow.getFollowers().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToFollow);
        log.debug("User with id of {} followed user with id of {} successfully", currentUser.getId(), userToFollow.getId());
    }

    @Override
    public void unFollow(User currentUser, User userToUnFollow) {
        currentUser.getFollowings().remove(userToUnFollow);
        userToUnFollow.getFollowers().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToUnFollow);
        log.debug("User with id of {} unfollowed user with id of {} successfully", currentUser.getId(), userToUnFollow.getId());
    }

    @Override
    public Set<User> getAllFollowers(User currentUser) {
        return currentUser.getFollowers();
    }

    @Override
    public Set<User> getAllFollowing(User currentUser) {
        return currentUser.getFollowings();
    }
}

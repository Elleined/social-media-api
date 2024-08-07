package com.elleined.socialmediaapi.service.follow;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService, FollowServiceRestriction {
    private final UserRepository userRepository;
    private final BlockService blockService;

    @Override
    public void follow(User currentUser, User userToFollow) {
        if (blockService.isBlockedByYou(currentUser, userToFollow))
            throw new BlockedException("Cannot follow because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, userToFollow))
            throw new BlockedException("Cannot follow because this user block you already!");

        if (alreadyFollows(currentUser, userToFollow))
            throw new ResourceAlreadyExistsException("Cannot follow because you've already followed this user!");

        currentUser.getFollowings().add(userToFollow);
        userToFollow.getFollowers().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToFollow);
        log.debug("User with id of {} followed user with id of {} successfully", currentUser.getId(), userToFollow.getId());
    }

    @Override
    public void unFollow(User currentUser, User userToUnFollow) {
        if (notFollows(currentUser, userToUnFollow))
            throw new ResourceNotFoundException("Cannot unfollow this user! because you're not following this user!");

        currentUser.getFollowings().remove(userToUnFollow);
        userToUnFollow.getFollowers().remove(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToUnFollow);
        log.debug("User with id of {} unfollowed user with id of {} successfully", currentUser.getId(), userToUnFollow.getId());
    }

    @Override
    public Page<User> getAllFollowers(User currentUser, Pageable pageable) {
        return userRepository.findAllFollowers(currentUser, pageable);
    }

    @Override
    public Page<User> getAllFollowing(User currentUser, Pageable pageable) {
        return userRepository.findAllFollowings(currentUser, pageable);
    }
}

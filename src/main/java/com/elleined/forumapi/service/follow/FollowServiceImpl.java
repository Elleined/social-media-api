package com.elleined.forumapi.service.follow;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.UserRepository;
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

    @Override
    public void follow(User currentUser, User userToFollow) {
        currentUser.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(currentUser);

        userRepository.save(currentUser);
        userRepository.save(userToFollow);
        log.debug("User with id of {} followed user with id of {} successfully", currentUser.getId(), userToFollow.getId());
    }

    @Override
    public void unFollow(User currentUser, User userToUnFollow) {
        currentUser.getFollowing().remove(userToUnFollow);
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
        return currentUser.getFollowing();
    }
}

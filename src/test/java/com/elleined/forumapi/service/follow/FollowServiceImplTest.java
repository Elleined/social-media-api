package com.elleined.forumapi.service.follow;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.UserRepository;
import com.elleined.forumapi.service.block.BlockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class FollowServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlockService blockService;

    @InjectMocks
    private FollowServiceImpl followService;


    @Test
    void follow() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = User.builder()
                .followings(new HashSet<>())
                .build();

        User userToFollow = User.builder()
                .followers(new HashSet<>())
                .build();

        // Stubbing methods
        when(blockService.isBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(blockService.isYouBeenBlockedBy(any(User.class), any(User.class))).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> followService.follow(currentUser, userToFollow));
        assertTrue(currentUser.getFollowings().contains(userToFollow));
        assertTrue(userToFollow.getFollowers().contains(currentUser));

        // Behavior Verifications
        verify(blockService).isBlockedBy(any(User.class), any(User.class));
        verify(blockService).isYouBeenBlockedBy(any(User.class), any(User.class));
        verify(userRepository, atMost(2)).save(any(User.class));
    }

    @Test
    void unFollow() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = User.builder()
                .followings(new HashSet<>())
                .build();

        User userToUnFollow = User.builder()
                .followers(new HashSet<>())
                .build();

        currentUser.getFollowings().add(userToUnFollow);
        userToUnFollow.getFollowers().add(currentUser);

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> followService.unFollow(currentUser, userToUnFollow));
        assertFalse(currentUser.getFollowings().contains(userToUnFollow));
        assertFalse(userToUnFollow.getFollowers().contains(currentUser));

        // Behavior Verifications
        verify(userRepository, atMost(2)).save(any(User.class));
    }

    @Test
    void getAllFollowers() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = User.builder()
                .followers(new HashSet<>())
                .build();

        // Stubbing methods

        // Calling the method
        assertEquals(0, currentUser.getFollowers().size());

        // Assertions

        // Behavior Verifications
    }

    @Test
    void getAllFollowing() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = User.builder()
                .followings(new HashSet<>())
                .build();

        // Stubbing methods

        // Calling the method
        assertEquals(0, currentUser.getFollowings().size());

        // Assertions

        // Behavior Verifications
    }
}
package com.elleined.socialmediaapi.service.block;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BlockServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlockServiceImpl blockService;

    @Test
    @DisplayName("Block user")
    void blockUser() {
        // Pre defined values

        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .id(1)
                .blockedUsers(new HashSet<>())
                .build();

        User userToBeBlocked = User.builder()
                .id(2)
                .blockedUsers(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        blockService.blockUser(currentUser, userToBeBlocked);

        // Behavior Verifications
        verify(userRepository, times(2)).save(any(User.class));

        // Assertions
        assertTrue(currentUser.getBlockedUsers().contains(userToBeBlocked));
        assertTrue(userToBeBlocked.getBlockedUsers().contains(currentUser));
    }

    @Test
    @DisplayName("Unblock user")
    void unBlockUser() {
        // Pre defined values

        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .id(1)
                .blockedUsers(new HashSet<>())
                .build();

        User userToBeUnblock = User.builder()
                .id(2)
                .blockedUsers(new HashSet<>())
                .build();


        // Set up method
        currentUser.getBlockedUsers().add(userToBeUnblock);
        userToBeUnblock.getBlockedUsers().add(currentUser);

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Calling the method
        blockService.unBlockUser(currentUser, userToBeUnblock);

        // Behavior Verifications
        verify(userRepository, times(2)).save(any(User.class));

        // Assertions
        assertFalse(currentUser.getBlockedUsers().contains(userToBeUnblock));
        assertFalse(userToBeUnblock.getBlockedUsers().contains(currentUser));
    }

    @Test
    @DisplayName("Is blocked by you")
    void isBlockedByYou() {
        // Pre defined values

        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .id(1)
                .blockedUsers(new HashSet<>())
                .build();

        User anotherUser = User.builder()
                .id(2)
                .blockedUsers(new HashSet<>())
                .build();

        // Set up method
        currentUser.getBlockedUsers().add(anotherUser);

        // Stubbing methods

        // Calling the method
        boolean isBlockedByYou = blockService.isBlockedByYou(currentUser, anotherUser);

        // Behavior Verifications

        // Assertions
        assertTrue(isBlockedByYou);
    }

    @Test
    @DisplayName("Is you been blocked by")
    void isYouBeenBlockedBy() {
        // Pre defined values

        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .id(1)
                .blockedUsers(new HashSet<>())
                .build();

        User anotherUser = User.builder()
                .id(2)
                .blockedUsers(new HashSet<>())
                .build();

        // Set up method
        anotherUser.getBlockedUsers().add(currentUser);

        // Stubbing methods

        // Calling the method
        boolean isYouBeenBlockedBy = blockService.isYouBeenBlockedBy(currentUser, anotherUser);

        // Behavior Verifications

        // Assertions
        assertTrue(isYouBeenBlockedBy);
    }

    @Test
    @DisplayName("Get all blocked users")
    void getAllBlockedUsers() {
        // Pre defined values

        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .id(1)
                .blockedUsers(new HashSet<>())
                .build();

        User anotherUser = User.builder()
                .id(2)
                .blockedUsers(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods
        when(userRepository.findAllBlockedUsers(any(User.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(anotherUser)));

        // Calling the method
        Set<User> currentUserBlockedUsers = blockService.getAllBlockedUsers(currentUser, Pageable.unpaged());

        // Behavior Verifications
        verify(userRepository).findAllBlockedUsers(any(User.class), any(Pageable.class));

        // Assertions
        assertNotNull(currentUserBlockedUsers);
        assertTrue(currentUserBlockedUsers.contains(anotherUser));
    }
}
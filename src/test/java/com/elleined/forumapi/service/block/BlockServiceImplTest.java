package com.elleined.forumapi.service.block;

import com.elleined.forumapi.model.User;
import com.elleined.forumapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlockServiceImpl blockService;

    @Test
    void blockUser() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);
        currentUser.setId(1);
        currentUser.setBlockedUsers(new HashSet<>());

        User userToBeBlocked = spy(User.class);
        userToBeBlocked.setId(2);

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> blockService.blockUser(currentUser, userToBeBlocked));
        assertTrue(currentUser.getBlockedUsers().contains(userToBeBlocked));

        // Behavior Verifications
        verify(userRepository).save(any(User.class));
    }

    @Test
    void unBlockUser() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);
        currentUser.setBlockedUsers(new HashSet<>());

        User userToBeUnblocked = spy(User.class);

        // Stubbing methods
        when(userRepository.save(any(User.class))).thenReturn(currentUser);
        currentUser.getBlockedUsers().add(userToBeUnblocked); // Initially block a user to be removed

        // Calling the method
        // Assertions

        assertDoesNotThrow(() -> blockService.unBlockUser(currentUser, userToBeUnblocked));
        assertFalse(currentUser.getBlockedUsers().contains(userToBeUnblocked));

        // Behavior Verifications
        verify(userRepository).save(any(User.class));
    }

    @Test
    void isBlockedBy() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);
        currentUser.setBlockedUsers(new HashSet<>());

        User userToCheck = spy(User.class);

        // Stubbing methods

        currentUser.getBlockedUsers().add(userToCheck); // Initially block the user to be checked if current user blocked the userToCheck

        // Calling the method
        // Assertions
        assertTrue(blockService.isBlockedBy(currentUser, userToCheck));

        // Behavior Verifications
    }

    @Test
    void isYouBeenBlockedBy() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);

        User suspectedUser = spy(User.class);
        suspectedUser.setBlockedUsers(new HashSet<>());

        // Stubbing methods
        suspectedUser.getBlockedUsers().add(currentUser); // Initially block the currentUser to be checked if suspected user blocked the currentUser

        // Calling the method
        // Assertions
        assertTrue(blockService.isYouBeenBlockedBy(currentUser, suspectedUser));

        // Behavior Verifications
    }

    @Test
    void getAllBlockedUsers() {
        // Expected and Actual Value

        // Mock Data
        User currentUser = spy(User.class);
        currentUser.setBlockedUsers(new HashSet<>());

        User userToBeBlocked1 = spy(User.class);
        User userToBeBlocked2 = spy(User.class);

        currentUser.getBlockedUsers().add(userToBeBlocked1);
        currentUser.getBlockedUsers().add(userToBeBlocked2);

        // Stubbing methods

        // Calling the method
        // Assertions
        assertDoesNotThrow(() -> blockService.getAllBlockedUsers(currentUser).forEach(System.out::println));

        // Behavior Verifications
    }
}
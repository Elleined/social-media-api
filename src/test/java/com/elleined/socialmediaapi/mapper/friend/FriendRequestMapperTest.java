package com.elleined.socialmediaapi.mapper.friend;

import com.elleined.socialmediaapi.dto.friend.FriendRequestDTO;
import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FriendRequestMapperTest {

    private final FriendRequestMapper friendRequestMapper = Mappers.getMapper(FriendRequestMapper.class);

    @Test
    @DisplayName("to DTO")
    void toDTO() {
        // Expected Value
        // Mock data
        FriendRequest expected = FriendRequest.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(new User())
                .requestedUser(new User())
                .notifications(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        FriendRequestDTO actual = friendRequestMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(expected.getCreator());

        assertNotNull(expected.getRequestedUser());

        assertNotNull(expected.getNotifications());
    }

    @Test
    @DisplayName("to entity")
    void toEntity() {
        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        FriendRequest actual = friendRequestMapper.toEntity(new User(), new User());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getRequestedUser());

        assertNotNull(actual.getNotifications());
    }
}
package com.elleined.socialmediaapi.mapper.hashtag;

import com.elleined.socialmediaapi.dto.hashtag.HashTagDTO;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class HashTagMapperTest {

    private final HashTagMapper hashTagMapper = Mappers.getMapper(HashTagMapper.class);

    @Test
    void toDTO() {
        // Pre defined values

        // Expected Value
        HashTag expected = HashTag.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .keyword("Keyword")
                .posts(new HashSet<>())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        HashTagDTO actual = hashTagMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(expected.getKeyword());

        assertNotNull(expected.getAllPostIds());
    }

    @Test
    void toEntity() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        HashTag actual = hashTagMapper.toEntity("Keyword");

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getKeyword());

        assertNotNull(actual.getAllPostIds());
    }
}
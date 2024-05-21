package com.elleined.socialmediaapi.mapper.vote;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.elleined.socialmediaapi.dto.vote.VoteDTO;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.vote.Vote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class VoteMapperTest {

    private final VoteMapper voteMapper = Mappers.getMapper(VoteMapper.class);

    @Test
    @DisplayName("")
    void toDTO() {
        // Pre defined values

        // Expected Value
        Vote expected = Vote.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .creator(User.builder()
                        .id(1)
                        .build())
                .comment(Comment.builder()
                        .id(1)
                        .build())
                .verdict(Vote.Verdict.DOWN_VOTE)
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        VoteDTO actual = voteMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertEquals(1, actual.getCreatorId());

        assertEquals(1, actual.getCommentId());

        assertNotNull(actual.getVerdict());
    }

    @Test
    @DisplayName("")
    void toEntity() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        Vote actual = voteMapper.toEntity(new User(), new Comment(), Vote.Verdict.DOWN_VOTE);

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getComment());

        assertNotNull(actual.getVerdict());
    }
}
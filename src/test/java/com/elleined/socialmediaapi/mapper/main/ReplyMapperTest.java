package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.ReplyDTO;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.reply.Reply;
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
class ReplyMapperTest {

    private final ReplyMapper replyMapper = Mappers.getMapper(ReplyMapper.class);

    @Test
    @DisplayName("")
    void toDTO() {
        // Pre defined values

        // Expected Value

        // Mock data
        Reply expected = Reply.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .body("Body")
                .status(Forum.Status.ACTIVE)
                .attachedPicture("Attached picture path")
                .creator(User.builder()
                        .id(1)
                        .build())
                .comment(Comment.builder()
                        .id(1)
                        .build())
                .hashTags(new HashSet<>())
                .mentions(new HashSet<>())
                .reactions(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        ReplyDTO actual = replyMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getBody());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertEquals(1, actual.getCreatorId());

        assertEquals(1, actual.getCommentId());

        assertNotNull(actual.getHashTagIds());

        assertNotNull(actual.getMentionIds());

        assertNotNull(actual.getReactionIds());
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
        Reply actual = replyMapper.toEntity(new User(),
                new Comment(),
                "Body",
                "Attached picture path",
                new HashSet<>(),
                new HashSet<>());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getComment());

        assertNotNull(actual.getHashTags());

        assertNotNull(actual.getMentions());

        assertNotNull(actual.getReactions());
    }
}
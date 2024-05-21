package com.elleined.socialmediaapi.mapper.main;

import static org.junit.jupiter.api.Assertions.*;

import com.elleined.socialmediaapi.dto.main.CommentDTO;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    @DisplayName("")
    void toDTO() {
        // Pre defined values

        // Expected Value
        Comment expected = Comment.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .body("Body")
                .status(Forum.Status.ACTIVE)
                .attachedPicture("Attached picture path")
                .creator(User.builder()
                        .id(1)
                        .build())
                .notifications(new HashSet<>())
                .post(Post.builder()
                        .id(1)
                        .build())
                .pinnedReply(Reply.builder()
                        .id(1)
                        .build())
                .hashTags(new HashSet<>())
                .mentions(new HashSet<>())
                .reactions(new HashSet<>())
                .replies(new ArrayList<>())
                .votes(new ArrayList<>())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        CommentDTO actual = commentMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getBody());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertEquals(1, actual.getCreatorId());

        assertNotNull(actual.getNotificationIds());

        assertEquals(1, actual.getPostId());

        assertEquals(1, actual.getPinnedReplyId());

        assertNotNull(actual.getHashTagIds());

        assertNotNull(actual.getMentionIds());

        assertNotNull(actual.getReactionIds());

        assertNotNull(actual.getReplyIds());

        assertNotNull(actual.getVoteIds());
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
        Comment actual = commentMapper.toEntity(new User(),
                new Post(),
                "Body",
                "Attached picture path",
                new HashSet<>(),
                new HashSet<>());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getBody());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getNotifications());

        assertNotNull(actual.getPost());

        assertNull(actual.getPinnedReply());

        assertNotNull(actual.getHashTags());

        assertNotNull(actual.getMentions());

        assertNotNull(actual.getReactions());

        assertNotNull(actual.getReplies());

        assertNotNull(actual.getVotes());
    }
}
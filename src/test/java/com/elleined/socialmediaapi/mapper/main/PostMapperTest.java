package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostMapperTest {

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    @DisplayName("")
    void toDTO() {
        // Pre defined values

        // Expected Value

        // Mock data
        Post expected = Post.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .body("Body")
                .status(Forum.Status.ACTIVE)
                .attachedPicture("Attached picture path")
                .creator(User.builder()
                        .id(1)
                        .build())
                .commentSectionStatus(Post.CommentSectionStatus.OPEN)
                .pinnedComment(Comment.builder()
                        .id(1)
                        .build())
                .hashTags(new HashSet<>())
                .mentions(new HashSet<>())
                .reactions(new HashSet<>())
                .comments(new ArrayList<>())
                .savingUsers(new HashSet<>())
                .sharers(new HashSet<>())
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        PostDTO actual = postMapper.toDTO(expected);

        // Behavior Verifications

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getBody());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertEquals(1, actual.getCreatorId());

        assertNotNull(actual.getCommentSectionStatus());

        assertEquals(1, actual.getPinnedCommentId());

        assertNotNull(actual.getHashTagIds());

        assertNotNull(actual.getMentionIds());

        assertNotNull(actual.getReactionIds());

        assertNotNull(actual.getCommentIds());

        assertNotNull(actual.getSavingUserIds());

        assertNotNull(actual.getSharerIds());
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
        Post actual = postMapper.toEntity(new User(),
                "Body",
                "Attached picture path",
                new HashSet<>(),
                new HashSet<>());

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getBody());

        assertNotNull(actual.getStatus());

        assertNotNull(actual.getAttachedPicture());

        assertNotNull(actual.getCreator());

        assertNotNull(actual.getCommentSectionStatus());

        assertNull(actual.getPinnedComment());

        assertNotNull(actual.getHashTags());

        assertNotNull(actual.getMentions());

        assertNotNull(actual.getReactions());

        assertNotNull(actual.getComments());

        assertNotNull(actual.getSavingUsers());

        assertNotNull(actual.getSharers());
    }
}
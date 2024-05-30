package com.elleined.socialmediaapi.mapper.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDTO() {
        // Pre defined values

        // Expected Value
        User expected = User.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .name("Name")
                .email("email@gmail.com")
                .picture("Picture path")
                .UUID("UUID")
                .note(Note.builder()
                        .id(1)
                        .build())
                .story(Story.builder()
                        .id(1)
                        .build())
                .sentFriendRequests(new HashSet<>())
                .receiveFriendRequests(new HashSet<>())
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .replies(new ArrayList<>())
                .savedPosts(new HashSet<>())
                .votedComments(new ArrayList<>())
                .sharedPosts(new HashSet<>())
                .blockedUsers(new HashSet<>())
                .friends(new HashSet<>())
                .followers(new HashSet<>())
                .followings(new HashSet<>())
                .reactions(new ArrayList<>())
                .build();

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method

        // Behavior Verifications
        UserDTO actual = userMapper.toDTO(expected);

        // Assertions
        assertEquals(expected.getId(), actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getName());

        assertNotNull(actual.getEmail());

        assertNotNull(actual.getPicture());

        assertNotNull(actual.getUUID());

        assertEquals(1, actual.getNoteId());

        assertEquals(1, actual.getStoryId());

        assertNotNull(actual.getSentFriendRequestIds());

        assertNotNull(actual.getReceiveFriendRequestIds());

        assertNotNull(actual.getPostIds());

        assertNotNull(actual.getCommentIds());

        assertNotNull(actual.getReplyIds());

        assertNotNull(actual.getSavedPostIds());

        assertNotNull(actual.getVotedCommentIds());

        assertNotNull(actual.getSharedPostIds());

        assertNotNull(actual.getBlockedUserIds());

        assertNotNull(actual.getFriendIds());

        assertNotNull(actual.getFollowerIds());

        assertNotNull(actual.getFollowingIds());

        assertNotNull(actual.getReactionIds());
    }

    @Test
    void toEntity() {
        // Pre defined values

        // Expected Value

        // Mock data

        // Set up method

        // Stubbing methods

        // Calling the method
        User actual = userMapper.toEntity("Name", "email@gmail.com", "Picture path");

        // Behavior Verifications

        // Assertions
        assertEquals(0, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());

        assertNotNull(actual.getName());

        assertNotNull(actual.getEmail());

        assertNotNull(actual.getPicture());

        assertNotNull(actual.getUUID());

        assertNull(actual.getNote());

        assertNull(actual.getStory());

        assertNotNull(actual.getSentFriendRequests());

        assertNotNull(actual.getReceiveFriendRequests());

        assertNotNull(actual.getPosts());

        assertNotNull(actual.getComments());

        assertNotNull(actual.getReplies());

        assertNotNull(actual.getSavedPosts());

        assertNotNull(actual.getVotedComments());

        assertNotNull(actual.getSharedPosts());

        assertNotNull(actual.getBlockedUsers());

        assertNotNull(actual.getFriends());

        assertNotNull(actual.getFollowers());

        assertNotNull(actual.getFollowings());

        assertNotNull(actual.getReactions());
    }
}
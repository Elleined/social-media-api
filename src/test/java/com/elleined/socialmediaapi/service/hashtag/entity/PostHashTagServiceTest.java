package com.elleined.socialmediaapi.service.hashtag.entity;

import com.elleined.socialmediaapi.mapper.hashtag.HashTagMapper;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.repository.hashtag.HashTagRepository;
import com.elleined.socialmediaapi.repository.main.PostRepository;
import com.elleined.socialmediaapi.service.hashtag.HashTagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostHashTagServiceTest {

    @Mock
    private HashTagRepository hashTagRepository;
    @Mock
    private HashTagService hashTagService;
    @Mock
    private HashTagMapper hashTagMapper;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostHashTagService postHashTagService;

    @Test
    void getAllByHashTagKeyword() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Post userOwnedPost = Post.builder()
                .author(currentUser)
                .build();

        Post otherUserPost = Post.builder()
                .author(new User())
                .build();

        // Set up method
        Set<Post> rawPosts = Set.of(userOwnedPost, otherUserPost);
        Set<Post> expectedPosts = Set.of(otherUserPost);

        // Stubbing methods
        when(hashTagRepository.getAllPostByHashTagKeyword(anyString())).thenReturn(rawPosts);

        // Calling the method

        // Assertions
        assertIterableEquals(expectedPosts, postHashTagService.getAllByHashTagKeyword(currentUser, "Keyword"));

        // Behavior Verifications
        verify(hashTagRepository).getAllPostByHashTagKeyword(anyString());
    }

    @Test
    void saveIfNotExists() {
        // Expected Value

        // Mock data
        Post comment = new Post();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashtag = new HashTag();
        expectedHashtag.setPosts(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.isExists(anyString())).thenReturn(true);
        when(hashTagMapper.toEntity(anyString())).thenReturn(expectedHashtag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashtag);
        when(postRepository.save(any(Post.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashtag, postHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashtag));
        assertTrue(expectedHashtag.getPosts().contains(comment));

        // Behavior Verifications
        verify(hashTagService).isExists(anyString());
        verify(hashTagMapper).toEntity(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(postRepository).save(any(Post.class));

        assertDoesNotThrow(() -> postHashTagService.save(comment, "Keyword"));
    }

    @Test
    void saveIfExisting() {
        // Expected Value

        // Mock data
        Post comment = new Post();
        comment.setHashTags(new HashSet<>());

        HashTag expectedHashTag = new HashTag();
        expectedHashTag.setPosts(new HashSet<>());

        // Set up method

        // Stubbing methods
        when(hashTagService.isExists(anyString())).thenReturn(false);
        when(hashTagService.getByKeyword(anyString())).thenReturn(expectedHashTag);
        when(hashTagService.save(any(HashTag.class))).thenReturn(expectedHashTag);
        when(postRepository.save(any(Post.class))).thenReturn(comment);

        // Calling the method
        // Assertions
        assertEquals(expectedHashTag, postHashTagService.save(comment, "Keyword"));
        assertTrue(comment.getHashTags().contains(expectedHashTag));
        assertTrue(expectedHashTag.getPosts().contains(comment));

        // Behavior Verifications
        verify(hashTagService).isExists(anyString());
        verify(hashTagService).getByKeyword(anyString());
        verify(hashTagService).save(any(HashTag.class));
        verify(postRepository).save(any(Post.class));

        assertDoesNotThrow(() -> postHashTagService.save(comment, "Keyword"));
    }

}
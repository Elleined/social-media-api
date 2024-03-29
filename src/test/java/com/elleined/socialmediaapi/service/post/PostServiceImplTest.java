package com.elleined.socialmediaapi.service.post;

import com.elleined.socialmediaapi.MultiPartFileDataFactory;
import com.elleined.socialmediaapi.mapper.PostMapper;
import com.elleined.socialmediaapi.model.Comment;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.Status;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.CommentRepository;
import com.elleined.socialmediaapi.repository.PostRepository;
import com.elleined.socialmediaapi.repository.UserRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.hashtag.entity.PostHashTagService;
import com.elleined.socialmediaapi.service.mention.PostMentionService;
import com.elleined.socialmediaapi.validator.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BlockService blockService;
    @Mock
    private PostMentionService postMentionService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostHashTagService postHashTagService;
    @Mock
    private Validator validator;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void save() throws IOException {
        // Expected Value

        // Mock data
        User currentUser = new User();
        String body = "Post Body";
        MultipartFile attachmentPicture = MultiPartFileDataFactory.notEmpty();
        Set<User> mentionedUsers = new HashSet<>();
        Set<String> keywords = new HashSet<>();

        Post post = new Post();

        // Set up method

        // Stubbing methods
        when(validator.isNotValid(anyString())).thenReturn(false);
        when(postMapper.toEntity(anyString(), any(User.class), anyString())).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(validator.isValid(anyCollection())).thenReturn(true);
        doNothing().when(postMentionService).mentionAll(any(User.class), anySet(), any(Post.class));
        when(postHashTagService.saveAll(any(Post.class), anySet())).thenReturn(new ArrayList<>());

        // Calling the method
        postService.save(currentUser, body, attachmentPicture, mentionedUsers, keywords);

        // Behavior Verifications
        verify(validator).isNotValid(anyString());
        verify(postMapper).toEntity(anyString(), any(User.class), anyString());
        verify(postRepository).save(any(Post.class));
        verify(validator, atMost(2)).isValid(anyCollection());
        verify(postMentionService).mentionAll(any(User.class), anySet(), any(Post.class));
        verify(postHashTagService).saveAll(any(Post.class), anySet());

        // Assertions
    }

    @Test
    void delete() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);

        List<Comment> comments = List.of(
                Comment.builder()
                        .status(Status.ACTIVE)
                        .build(),
                Comment.builder()
                        .status(Status.ACTIVE)
                        .build()
        );
        Post post = Post.builder()
                .status(Status.ACTIVE)
                .comments(comments)
                .build();
        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Post.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(commentRepository.saveAll(anyList())).thenReturn(comments);

        // Calling the method
        postService.delete(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));
        verify(commentRepository).saveAll(anyList());

        // Assertions
        assertTrue(post.isInactive());
        assertTrue(post.getComments().stream().allMatch(Comment::isInactive));
    }

    @Test
    void updateBody() {
        // Expected Value
        String oldBody = "Old Body";

        // Mock data
        User currentUser = spy(User.class);
        Post post = Post.builder()
                .body(oldBody)
                .build();

        String newBody = "New Body";

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Post.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Calling the method
        postService.updateBody(currentUser, post, newBody);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));

        // Assertions
        assertEquals(newBody, post.getBody());
    }

    @Test
    @DisplayName("Post with open comment section to closed")
    void openCommentSectionToClosed() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Post post = spy(Post.class);
        post.setCommentSectionStatus(Post.CommentSectionStatus.OPEN);

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Post.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Calling the method
        postService.updateCommentSectionStatus(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));

        // Assertions
        assertEquals(Post.CommentSectionStatus.CLOSED, post.getCommentSectionStatus());
    }

    @Test
    @DisplayName("Post with closed comment section to open")
    void closedCommentSectionToOpen() {
        // Expected Value

        // Mock data
        User currentUser = spy(User.class);
        Post post = spy(Post.class);
        post.setCommentSectionStatus(Post.CommentSectionStatus.CLOSED);

        // Set up method

        // Stubbing methods
        doReturn(false).when(currentUser).notOwned(any(Post.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Calling the method
        postService.updateCommentSectionStatus(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));

        // Assertions
        assertEquals(Post.CommentSectionStatus.OPEN, post.getCommentSectionStatus());
    }

    @Test
    void getAll() {
        // Expected Value

        // Mock data
        User currentUser = new User();
        Post inactivePost = Post.builder()
                .status(Status.INACTIVE)
                .build();

        Post post1 = Post.builder()
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now().plusMinutes(1))
                .build();

        Post post2 = Post.builder()
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now().plusMinutes(2))
                .build();

        // Set up method
        List<Post> rawPosts = List.of(inactivePost, post2, post1);
        List<Post> expectedPost = List.of(post2, post1);

        // Stubbing methods
        when(postRepository.findAll()).thenReturn(rawPosts);

        // Calling the method
        List<Post> actualPosts = postService.getAll(currentUser);

        // Behavior Verifications
        verify(postRepository).findAll();

        // Assertions
        assertIterableEquals(expectedPost, actualPosts);
    }

    @Test
    void savedPost() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .savedPosts(new HashSet<>())
                .build();

        Post post = spy(Post.class);
        post.setSavingUsers(new HashSet<>());

        // Set up method

        // Stubbing methods
        doReturn(false).when(post).isInactive();
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // Calling the method
        postService.savedPost(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));
        verify(userRepository).save(any(User.class));

        // Assertions
        assertTrue(currentUser.getSavedPosts().contains(post));
        assertTrue(post.getSavingUsers().contains(currentUser));
    }

    @Test
    void unSavedPost() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .savedPosts(new HashSet<>())
                .build();

        Post post = Post.builder()
                .savingUsers(new HashSet<>())
                .build();

        // Set up method
        currentUser.getSavedPosts().add(post);
        post.getSavingUsers().add(currentUser);

        // Stubbing methods
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // Calling the method
        postService.unSavedPost(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));
        verify(userRepository).save(any(User.class));

        // Assertions
        assertFalse(currentUser.getSavedPosts().contains(post));
        assertFalse(post.getSavingUsers().contains(currentUser));
    }

    @Test
    void getAllSavedPosts() {
        // Expected Value

        // Mock data
        Post inactivePost = Post.builder()
                .status(Status.INACTIVE)
                .build();

        Post post1 = Post.builder()
                .status(Status.ACTIVE)
                .build();

        Post post2 = Post.builder()
                .status(Status.ACTIVE)
                .build();

        User currentUser = User.builder()
                .savedPosts(Set.of(inactivePost, post1, post2))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        Set<Post> actualSavedPosts = postService.getAllSavedPosts(currentUser);

        // Behavior Verifications

        // Assertions
        assertTrue(actualSavedPosts.contains(post1));
        assertTrue(actualSavedPosts.contains(post2));
    }


    @Test
    void sharePost() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .sharedPosts(new HashSet<>())
                .build();

        Post post = spy(Post.class);
        post.setSharers(new HashSet<>());

        // Set up method

        // Stubbing methods
        doReturn(false).when(post).isInactive();
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // Calling the method
        postService.sharePost(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));
        verify(userRepository).save(any(User.class));

        // Assertions
        assertTrue(currentUser.getSharedPosts().contains(post));
        assertTrue(post.getSharers().contains(currentUser));
    }

    @Test
    void unSharePost() {
        // Expected Value

        // Mock data
        User currentUser = User.builder()
                .sharedPosts(new HashSet<>())
                .build();

        Post post = Post.builder()
                .sharers(new HashSet<>())
                .build();

        // Set up method
        currentUser.getSharedPosts().add(post);
        post.getSharers().add(currentUser);

        // Stubbing methods
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // Calling the method
        postService.unSharePost(currentUser, post);

        // Behavior Verifications
        verify(postRepository).save(any(Post.class));
        verify(userRepository).save(any(User.class));

        // Assertions
        assertFalse(currentUser.getSharedPosts().contains(post));
        assertFalse(post.getSharers().contains(currentUser));
    }

    @Test
    void getAllSharedPosts() {
        // Expected Value

        // Mock data
        Post inactivePost = Post.builder()
                .status(Status.INACTIVE)
                .build();

        Post post1 = Post.builder()
                .status(Status.ACTIVE)
                .build();

        Post post2 = Post.builder()
                .status(Status.ACTIVE)
                .build();

        User currentUser = User.builder()
                .sharedPosts(Set.of(inactivePost, post1, post2))
                .build();

        // Set up method

        // Stubbing methods

        // Calling the method
        Set<Post> actualSharePosts = postService.getAllSharedPosts(currentUser);

        // Behavior Verifications

        // Assertions
        assertTrue(actualSharePosts.contains(post1));
        assertTrue(actualSharePosts.contains(post2));
    }
}
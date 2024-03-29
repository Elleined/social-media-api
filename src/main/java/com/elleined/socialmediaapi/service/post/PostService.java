package com.elleined.socialmediaapi.service.post;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.EmptyBodyException;
import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.model.Post;
import com.elleined.socialmediaapi.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PostService extends SavedPostService, SharePostService {

    Post save(User currentUser,
              String body,
              MultipartFile attachedPicture,
              Set<User> mentionedUsers,
              Set<String> keywords)
            throws EmptyBodyException,
            BlockedException,
            ResourceNotFoundException,
            IOException;

    void delete(User currentUser, Post post) throws NotOwnedException;

    Post updateBody(User currentUser, Post post, String newBody)
            throws ResourceNotFoundException,
            NotOwnedException;

    Post updateCommentSectionStatus(User currentUser, Post post);

    Post getById(int postId) throws ResourceNotFoundException;

    List<Post> getAll(User currentUser);

    @Override
    Post savedPost(User currentUser, Post postToSaved);

    @Override
    void unSavedPost(User currentUser, Post postToUnSave);

    @Override
    Set<Post> getAllSavedPosts(User currentUser);

    @Override
    Post sharePost(User currentUser, Post postToShare);

    @Override
    void unSharePost(User currentUser, Post postToUnShare);

    @Override
    Set<Post> getAllSharedPosts(User currentUser);
}

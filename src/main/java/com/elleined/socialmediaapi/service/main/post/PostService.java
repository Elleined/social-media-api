package com.elleined.socialmediaapi.service.main.post;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PostService extends SavedPostService, SharePostService, CustomService<Post> {

    Post save(User currentUser,
              String body,
              MultipartFile attachedPicture,
              Set<User> mentionedUsers,
              Set<String> keywords)
            throws BlockedException,
            ResourceNotFoundException,
            IOException;

    void delete(User currentUser, Post post) throws ResourceNotOwnedException;

    Post update(User currentUser, Post post, String newBody, MultipartFile newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException;

    Post updateCommentSectionStatus(User currentUser, Post post);

    List<Post> getAll(User currentUser, Pageable pageable);

    void reactivate(User currentUser, Post post);

    void updateStatus(User currentUser, Post post, Forum.Status status);
}

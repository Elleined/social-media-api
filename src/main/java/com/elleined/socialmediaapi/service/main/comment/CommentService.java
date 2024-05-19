package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.exception.CommentSectionException;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CommentService extends CustomService<Comment> {
    Comment save(User currentUser,
                 Post post,
                 String body,
                 MultipartFile attachedPicture,
                 Set<User> mentionedUsers)
            throws ResourceNotFoundException,
            CommentSectionException,
            BlockedException,
            IOException;

    List<Comment> getAll(User currentUser, Post post) throws ResourceNotFoundException;

    void delete(User currentUser, Post post, Comment comment);

    Comment update(User currentUser, Post post, Comment comment, String newBody, String newAttachedPicture)
            throws ResourceNotFoundException,
            ResourceNotOwnedException;

    void reactivate(Comment comment);
}

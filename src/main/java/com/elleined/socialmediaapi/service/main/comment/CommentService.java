package com.elleined.socialmediaapi.service.main.comment;

import com.elleined.socialmediaapi.exception.CommentSectionException;
import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Forum;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.CustomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface CommentService extends CustomService<Comment> {
    Comment save(User currentUser,
                 Post post,
                 String body,
                 List<MultipartFile> attachedPictures,
                 Set<Mention> mentions,
                 Set<HashTag> hashTags)
            throws ResourceNotFoundException,
            CommentSectionException,
            BlockedException;

    Page<Comment> getAll(User currentUser, Post post, Pageable pageable) throws ResourceNotFoundException;

    void delete(User currentUser, Post post, Comment comment);

    void update(User currentUser,
                Post post,
                Comment comment,
                String newBody,
                List<MultipartFile> attachedPictures)
            throws ResourceNotFoundException,
            ResourceNotOwnedException;

    void reactivate(User currentUser, Post post, Comment comment);

    void updateStatus(User currentUser, Post post, Comment comment, Forum.Status status);
}

package com.elleined.socialmediaapi.service.vote;

import com.elleined.socialmediaapi.exception.block.BlockedException;
import com.elleined.socialmediaapi.exception.resource.ResourceAlreadyExistsException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotFoundException;
import com.elleined.socialmediaapi.exception.resource.ResourceNotOwnedException;
import com.elleined.socialmediaapi.mapper.vote.VoteMapper;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.vote.Vote;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.main.VoteRepository;
import com.elleined.socialmediaapi.request.main.VoteRequest;
import com.elleined.socialmediaapi.service.block.BlockService;
import com.elleined.socialmediaapi.service.main.comment.CommentService;
import com.elleined.socialmediaapi.service.main.post.PostService;
import com.elleined.socialmediaapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    private final BlockService blockService;

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Vote getById(int id) throws ResourceNotFoundException {
        return voteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vote with id of " + id + " doesn't exists!"));
    }

    @Override
    public List<Vote> getAllById(List<Integer> ids) {
        return voteRepository.findAllById(ids);
    }

    @Override
    public Vote save(VoteRequest voteRequest) {
        User currentUser = userService.getById(voteRequest.getCreatorId());
        Post post = postService.getById(voteRequest.getPostId());
        Comment comment = commentService.getById(voteRequest.getCommentId());

        if (currentUser.isAlreadyVoted(comment))
            throw new ResourceAlreadyExistsException("Cannot vote this comment! because you already voted this comment!");

        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot vote this comment! because this post doesn't have this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot vote this comment! because this comment associated post might already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot vote this comment! because this comment might already deleted or doesn't exists!");

        if (blockService.isBlockedByYou(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot vote this comment! because you blocked this user already!");

        if (blockService.isYouBeenBlockedBy(currentUser, comment.getCreator()))
            throw new BlockedException("Cannot vote this comment! because this user block you already!");

        Vote vote = voteMapper.toEntity(currentUser, comment, voteRequest.getVerdict());
        voteRepository.save(vote);

        comment.getVotes().add(vote);
        commentService.save(comment);

        log.debug("Saving vote success to comment with id of {}", comment.getId());
        return vote;
    }

    @Override
    public List<Vote> getAll(Post post, Comment comment) {
        if (post.notOwned(comment))
            throw new ResourceNotOwnedException("Cannot get all vote to this comment! because this post doesn't have this comment!");

        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot get all vote to this comment! because this comment associated post might already deleted or doesn't exists!");

        if (comment.isInactive())
            throw new ResourceNotFoundException("Cannot get all vote to this comment! because this comment might already deleted or doesn't exists!");

        return comment.getVotes().stream()
                .sorted(Comparator.comparing(PrimaryKeyIdentity::getCreatedAt).reversed())
                .toList();
    }
}
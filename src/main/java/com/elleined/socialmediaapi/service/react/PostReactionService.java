package com.elleined.socialmediaapi.service.react;

import com.elleined.socialmediaapi.exception.BlockedException;
import com.elleined.socialmediaapi.exception.NotOwnedException;
import com.elleined.socialmediaapi.exception.ResourceNotFoundException;
import com.elleined.socialmediaapi.mapper.react.PostReactionMapper;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.model.react.Emoji;
import com.elleined.socialmediaapi.model.react.React;
import com.elleined.socialmediaapi.repository.react.PostReactRepository;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostReactionService implements ReactionService<Post, PostReact> {
    private final BlockService blockService;

    private final PostReactRepository postReactRepository;
    private final PostReactionMapper postReactionMapper;

    @Override
    public PostReact getById(int id) throws ResourceNotFoundException {
        return postReactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reaction with id of " + id + " doesn't exists!"));
    }

    @Override
    public PostReact getByUserReaction(User currentUser, Post post) {
        return post.getReactions().stream()
                .filter(postReact -> postReact.getRespondent().equals(currentUser))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<PostReact> getAll(Post post) {
        return post.getReactions().stream()
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<PostReact> getAllReactionByEmojiType(Post post, Emoji.Type type) {
        return post.getReactions().stream()
                .filter(postReact -> postReact.getEmoji().getType().equals(type))
                .sorted(Comparator.comparing(React::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public PostReact save(User currentUser, Post post, Emoji emoji) {
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot react to this post! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot react to this post! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot react to this post! because this user block you already!");

        PostReact postReact = postReactionMapper.toEntity(currentUser, post, emoji);
        postReactRepository.save(postReact);
        log.debug("User with id of {} successfully reacted with id of {} in post with id of {}", currentUser.getId(), emoji.getId(), post.getId());
        return postReact;
    }

    @Override
    public void update(User currentUser, Post post, PostReact postReact, Emoji emoji) {
        if (currentUser.notOwned(postReact))
            throw new NotOwnedException("Cannot update react to this post! because you don't own this reaction");
        if (post.isInactive())
            throw new ResourceNotFoundException("Cannot update react to this post! because this might be already deleted or doesn't exists!");
        if (blockService.isBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot update react to this post! because you blocked this user already!");
        if (blockService.isYouBeenBlockedBy(currentUser, post.getAuthor()))
            throw new BlockedException("Cannot update react to this post! because this user block you already!");

        postReact.setEmoji(emoji);
        postReactRepository.save(postReact);
        log.debug("User with id of {} updated his/her reaction to post with id of {} to emoji with id of {}", currentUser.getId(), post.getId(), emoji.getId());
    }

    @Override
    public void delete(User currentUser, PostReact postReact) {
        if (currentUser.notOwned(postReact))
            throw new NotOwnedException("Cannot delete this post reaction! because you dont owned this reaction!");
        postReactRepository.delete(postReact);
        log.debug("Reaction with id of {} removed successfully", postReact.getId());
    }
}

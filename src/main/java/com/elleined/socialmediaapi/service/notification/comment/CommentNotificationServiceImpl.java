package com.elleined.socialmediaapi.service.notification.comment;

import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.service.block.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentNotificationServiceImpl implements CommentNotificationService {
    private final BlockService blockService;

    @Override
    public List<Comment> getAllUnreadNotification(User currentUser) {
        return currentUser.getPosts().stream()
                .map(Post::getComments)
                .flatMap(comments -> comments.stream()
                        .filter(Comment::isActive)
                        .filter(Comment::isUnread)
                        .filter(comment -> !blockService.isBlockedBy(currentUser, comment.getCommenter()))
                        .filter(comment -> !blockService.isYouBeenBlockedBy(currentUser, comment.getCommenter())))
                .toList();
    }
}

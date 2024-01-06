package com.elleined.forumapi.service.notification.comment;

import com.elleined.forumapi.model.*;
import com.elleined.forumapi.service.block.BlockService;
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

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }

    @Override
    public int notificationCountForCommenter(User author, Post post, User commenter) {
        return (int) post.getComments()
                .stream()
                .filter(Comment::isActive)
                .filter(Comment::isUnread)
                .filter(comment -> comment.getCommenter().equals(commenter))
                .filter(comment -> !blockService.isBlockedBy(author, comment.getCommenter()))
                .filter(comment -> !blockService.isYouBeenBlockedBy(author, comment.getCommenter()))
                .count();
    }
}

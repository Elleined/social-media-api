package com.elleined.socialmediaapi.service.notification.comment.reader;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.repository.mention.MentionRepository;
import com.elleined.socialmediaapi.service.notification.mention.CommentMentionNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Qualifier("commentMentionNotificationReader")
public class CommentMentionNotificationReader implements CommentNotificationReaderService {
    private final CommentMentionNotificationService commentMentionNotificationService;
    private final MentionRepository mentionRepository;

    @Override
    public void readAll(User currentUser, Post post) {
        List<CommentMention> receiveUnreadCommentMentions = commentMentionNotificationService.getAllUnreadNotification(currentUser);
        receiveUnreadCommentMentions.stream()
                .filter(commentMention -> commentMention.getComment().getPost().equals(post))
                .forEach(commentMention -> commentMention.setNotificationStatus(NotificationStatus.READ));
        mentionRepository.saveAll(receiveUnreadCommentMentions);
        log.debug("Reading all comment mentions for current user with id of {} success", currentUser.getId());
    }
}

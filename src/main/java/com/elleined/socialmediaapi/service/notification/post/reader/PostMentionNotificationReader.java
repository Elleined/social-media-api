package com.elleined.socialmediaapi.service.notification.post.reader;

import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.repository.MentionRepository;
import com.elleined.socialmediaapi.service.notification.mention.PostMentionNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Primary
public class PostMentionNotificationReader implements PostNotificationReader {
    private final PostMentionNotificationService postMentionNotificationService;
    private final MentionRepository mentionRepository;

    @Override
    public void readAll(User currentUser) {
        List<PostMention> receiveUnreadPostMentions = postMentionNotificationService.getAllUnreadNotification(currentUser);
        receiveUnreadPostMentions.forEach(postMention -> postMention.setNotificationStatus(NotificationStatus.READ));
        mentionRepository.saveAll(receiveUnreadPostMentions);
        log.debug("Reading all post mentions for current user with id of {} success", currentUser.getId());
    }
}

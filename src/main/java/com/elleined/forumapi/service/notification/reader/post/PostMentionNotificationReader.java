package com.elleined.forumapi.service.notification.reader.post;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.notification.mention.MentionNotificationService;
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
public class PostMentionNotificationReader implements PostNotificationReaderService {
    private final MentionNotificationService<PostMention> postMentionNotificationService;
    private final MentionRepository mentionRepository;

    @Override
    public void readAll(User currentUser) {
        List<PostMention> receiveUnreadPostMentions = postMentionNotificationService.getAllUnreadNotification(currentUser);
        receiveUnreadPostMentions.forEach(postMention -> postMention.setNotificationStatus(NotificationStatus.READ));
        mentionRepository.saveAll(receiveUnreadPostMentions);
        log.debug("Reading all post mentions for current user with id of {} success", currentUser.getId());
    }
}

package com.elleined.forumapi.service.notification.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostMentionNotificationService implements MentionNotificationService<PostMention> {
    private final BlockService blockService;
    @Override
    public List<PostMention> getAllNotification(User currentUser) {
        return currentUser.getReceivePostMentions()
                .stream()
                .filter(mention -> !blockService.isBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> !blockService.isYouBeenBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> mention.getPost().getStatus() == Status.ACTIVE)
                .filter(mention -> mention.getNotificationStatus() == NotificationStatus.UNREAD)
                .toList();
    }
}

package com.elleined.forumapi.service.notification.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.ReplyMention;
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
public class ReplyMentionNotificationService implements MentionNotificationService<ReplyMention> {
    private final BlockService blockService;
    @Override
    public List<ReplyMention> getAllUnreadNotification(User currentUser) {
        return currentUser.getReceiveReplyMentions()
                .stream()
                .filter(mention -> !blockService.isBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> !blockService.isYouBeenBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> mention.getReply().getStatus() == Status.ACTIVE)
                .filter(mention -> mention.getNotificationStatus() == NotificationStatus.UNREAD)
                .toList();
    }

    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }
}

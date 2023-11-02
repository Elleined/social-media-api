package com.elleined.forumapi.service.notification.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Status;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.CommentMention;
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
public class CommentMentionNotificationService implements MentionNotificationService<CommentMention> {
    private final BlockService blockService;
    @Override
    public List<CommentMention> getAllNotification(User currentUser) {
        return currentUser.getReceiveCommentMentions()
                .stream()
                .filter(mention -> !blockService.isBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> !blockService.isYouBeenBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> mention.getComment().getStatus() == Status.ACTIVE)
                .filter(mention -> mention.getNotificationStatus() == NotificationStatus.UNREAD)
                .toList();
    }
}

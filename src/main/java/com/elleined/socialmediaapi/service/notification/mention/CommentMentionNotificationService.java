package com.elleined.socialmediaapi.service.notification.mention;

import com.elleined.socialmediaapi.model.User;
import com.elleined.socialmediaapi.model.mention.Mention;
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
public class CommentMentionNotificationService implements MentionNotificationService<CommentMention> {
    private final BlockService blockService;
    @Override
    public List<CommentMention> getAllUnreadNotification(User currentUser) {
        return currentUser.getReceiveCommentMentions().stream()
                .filter(Mention::isEntityActive)
                .filter(Mention::isUnread)
                .filter(mention -> !blockService.isBlockedBy(currentUser, mention.getMentionedUser()))
                .filter(mention -> !blockService.isYouBeenBlockedBy(currentUser, mention.getMentionedUser()))
                .toList();
    }
}

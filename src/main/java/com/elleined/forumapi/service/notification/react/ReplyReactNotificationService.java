package com.elleined.forumapi.service.notification.react;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.react.React;
import com.elleined.forumapi.model.react.ReplyReact;
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
public class ReplyReactNotificationService implements ReactNotificationService<ReplyReact> {
    private final BlockService blockService;
    @Override
    public int getNotificationCount(User currentUser) {
        return getAllUnreadNotification(currentUser).size();
    }

    @Override
    public List<ReplyReact> getAllUnreadNotification(User currentUser) {
        return currentUser.getReplies().stream()
                .filter(Reply::isActive)
                .map(Reply::getReactions)
                .flatMap(reactions -> reactions.stream()
                        .filter(React::isUnread)
                        .filter(reaction -> !blockService.isBlockedBy(currentUser, reaction.getRespondent()))
                        .filter(reaction -> !blockService.isYouBeenBlockedBy(currentUser, reaction.getRespondent())))
                .toList();
    }
}

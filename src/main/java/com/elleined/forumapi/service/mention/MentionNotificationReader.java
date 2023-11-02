package com.elleined.forumapi.service.mention;

import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.Mention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.repository.MentionRepository;
import com.elleined.forumapi.service.NotificationReader;
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
public class MentionNotificationReader implements NotificationReader {
    private final PostMentionService postMentionService;
    private final CommentMentionService commentMentionService;
    private final ReplyMentionService replyMentionService;
    private final MentionRepository mentionRepository;

    @Override
    public void read(User currentUser) {
        List<PostMention> receiveUnreadPostMentions = postMentionService.getAllUnreadNotification(currentUser);
        receiveUnreadPostMentions.forEach(this::read);
        mentionRepository.saveAll(receiveUnreadPostMentions);
        log.debug("Reading all post mentions for current user with id of {} success", currentUser.getId());
    }

    @Override
    public void read(User currentUser, Post post) {
        List<CommentMention> receiveUnreadCommentMentions = commentMentionService.getAllUnreadNotification(currentUser);
        receiveUnreadCommentMentions.stream()
                .filter(mention -> mention.getComment().getPost().equals(post))
                .forEach(this::read);
        mentionRepository.saveAll(receiveUnreadCommentMentions);
        log.debug("Reading all comment mentions for current user with id of {} success", currentUser.getId());
    }

    @Override
    public void read(User currentUser, Comment comment) {
        List<ReplyMention> receiveUnreadReplyMentions = replyMentionService.getAllUnreadNotification(currentUser);
        receiveUnreadReplyMentions.stream()
                .filter(mention -> mention.getReply().getComment().equals(comment))
                .forEach(this::read);
        mentionRepository.saveAll(receiveUnreadReplyMentions);
        log.debug("Reading all reply mentions for current user with id of {} success", currentUser.getId());
    }
    private void read(Mention mention) {
        mention.setNotificationStatus(NotificationStatus.READ);
    }
}

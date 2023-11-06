package com.elleined.forumapi.mapper;

import com.elleined.forumapi.dto.notification.CommentNotification;
import com.elleined.forumapi.dto.notification.PostNotification;
import com.elleined.forumapi.dto.notification.ReplyNotification;
import com.elleined.forumapi.model.Comment;
import com.elleined.forumapi.model.ModalTracker;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.like.CommentLike;
import com.elleined.forumapi.model.like.PostLike;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.model.mention.CommentMention;
import com.elleined.forumapi.model.mention.PostMention;
import com.elleined.forumapi.model.mention.ReplyMention;
import com.elleined.forumapi.service.CommentService;
import com.elleined.forumapi.service.Formatter;
import com.elleined.forumapi.service.ReplyService;
import com.elleined.forumapi.service.notification.comment.CommentNotificationService;
import com.elleined.forumapi.service.notification.reply.ReplyNotificationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", imports = {Formatter.class, ModalTracker.Type.class})
public abstract class NotificationMapper {

    @Autowired
    @Lazy
    protected CommentService commentService;

    protected CommentNotificationService commentNotificationService;
    protected ReplyNotificationService replyNotificationService;
    @Autowired @Lazy
    protected ReplyService replyService;

    @Mappings(value = {
            @Mapping(target = "id", source = "comment.id"),
            @Mapping(target = "receiverId", source = "comment.commenter.id"),
            @Mapping(target = "message", expression = "java(getMessage(comment))"),
            @Mapping(target = "respondentPicture", source = "comment.commenter.picture"),
            @Mapping(target = "respondentId", source = "comment.commenter.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(comment.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(comment.getDateCreated()))"),
            @Mapping(target = "notificationStatus", source = "comment.notificationStatus"),

            @Mapping(target = "postId", source = "comment.post.id"),
            @Mapping(target = "commentId", source = "comment.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(comment))"),
    })
    public abstract CommentNotification toNotification(Comment comment);

    @Mappings(value = {
            @Mapping(target = "id", source = "reply.id"),
            @Mapping(target = "receiverId", source = "reply.replier.id"),
            @Mapping(target = "message", expression = "java(getMessage(reply))"),
            @Mapping(target = "respondentPicture", source = "reply.replier.picture"),
            @Mapping(target = "respondentId", source = "reply.replier.id"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(reply.getDateCreated()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(reply.getDateCreated()))"),
            @Mapping(target = "notificationStatus", source = "reply.notificationStatus"),

            @Mapping(target = "postId", source = "reply.comment.post.id"),
            @Mapping(target = "commentId", source = "reply.comment.id"),
            @Mapping(target = "replyId", source = "reply.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(reply))"),
    })
    public abstract ReplyNotification toNotification(Reply reply);

    @Mappings({
            @Mapping(target = "id", source = "postMention.id"),
            @Mapping(target = "receiverId", expression = "java(postMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postMention.getMessage())"),
            @Mapping(target = "respondentId", source = "postMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "postMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "postMention.notificationStatus"),

            @Mapping(target = "postId", source = "postMention.post.id"),
    })
    public abstract PostNotification toMentionNotification(PostMention postMention);

    @Mappings({
            @Mapping(target = "id", source = "commentMention.id"),
            @Mapping(target = "receiverId", expression = "java(commentMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(commentMention.getMessage())"),
            @Mapping(target = "respondentId", source = "commentMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "commentMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "commentMention.notificationStatus"),

            @Mapping(target = "postId", source = "commentMention.comment.post.id"),
            @Mapping(target = "commentId", source = "commentMention.comment.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    public abstract CommentNotification toMentionNotification(CommentMention commentMention);

    @Mappings({
            @Mapping(target = "id", source = "replyMention.id"),
            @Mapping(target = "receiverId", expression = "java(replyMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(replyMention.getMessage())"),
            @Mapping(target = "respondentId", source = "replyMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "replyMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "postId", source = "replyMention.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyMention.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyMention.reply.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    public abstract ReplyNotification toMentionNotification(ReplyMention replyMention);

    @Mappings(value = {
            @Mapping(target = "id", source = "postLike.id"),
            @Mapping(target = "receiverId", expression = "java(postLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(postLike.getMessage())"),
            @Mapping(target = "respondentId", source = "postLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "postLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(postLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(postLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "postLike.notificationStatus"),

            @Mapping(target = "postId", source = "postLike.post.id"),
    })
    public abstract PostNotification toLikeNotification(PostLike postLike);

    @Mappings(value = {
            @Mapping(target = "id", source = "commentLike.id"),
            @Mapping(target = "receiverId", expression = "java(commentLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(commentLike.getMessage())"),
            @Mapping(target = "respondentId", source = "commentLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "commentLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "commentLike.notificationStatus"),

            @Mapping(target = "postId", source = "commentLike.comment.post.id"),
            @Mapping(target = "commentId", source = "commentLike.comment.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    public abstract CommentNotification toLikeNotification(CommentLike commentLike);

    @Mappings(value = {
            @Mapping(target = "id", source = "replyLike.id"),
            @Mapping(target = "receiverId", expression = "java(replyLike.getReceiverId())"),
            @Mapping(target = "message", expression = "java(replyLike.getMessage())"),
            @Mapping(target = "respondentId", source = "replyLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "replyLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "replyLike.notificationStatus"),

            @Mapping(target = "postId", source = "replyLike.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyLike.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyLike.reply.id"),
            @Mapping(target = "count", expression = "java(1)")
    })
    public abstract ReplyNotification toLikeNotification(ReplyLike replyLike);

    protected String getMessage(Comment comment) {
        return comment.getCommenter().getName() + " commented in your post: " + "\"" + comment.getPost().getBody() + "\"";
    }

    protected String getMessage(Reply reply) {
        return reply.getReplier().getName() + " replied to your comment: " + "\"" + reply.getComment().getBody() + "\"";
    }

    protected int getNotificationCount(Comment comment) {
        return commentNotificationService.notificationCountForCommenter(comment.getPost().getAuthor(), comment.getPost(), comment.getCommenter());
    }

    protected int getNotificationCount(Reply reply) {
        return replyNotificationService.notificationCountForReplier(reply.getComment().getCommenter(), reply.getComment(), reply.getReplier());
    }
}


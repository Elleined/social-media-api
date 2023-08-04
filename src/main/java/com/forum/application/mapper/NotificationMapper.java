package com.forum.application.mapper;

import com.forum.application.dto.CommentDTO;
import com.forum.application.dto.ReplyDTO;
import com.forum.application.dto.notification.CommentNotification;
import com.forum.application.dto.notification.PostNotification;
import com.forum.application.dto.notification.ReplyNotification;
import com.forum.application.model.ModalTracker;
import com.forum.application.model.User;
import com.forum.application.model.like.CommentLike;
import com.forum.application.model.like.PostLike;
import com.forum.application.model.like.ReplyLike;
import com.forum.application.model.mention.CommentMention;
import com.forum.application.model.mention.PostMention;
import com.forum.application.model.mention.ReplyMention;
import com.forum.application.service.CommentService;
import com.forum.application.service.Formatter;
import com.forum.application.service.ReplyService;
import com.forum.application.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", imports = {Formatter.class, ModalTracker.Type.class})
public abstract class NotificationMapper {

    @Autowired
    @Lazy
    protected UserService userService;

    @Autowired
    @Lazy
    protected CommentService commentService;
    @Autowired @Lazy
    protected ReplyService replyService;

    @Mappings(value = {
            @Mapping(target = "id", source = "commentDTO.id"),
            @Mapping(target = "receiverId", source = "commentDTO.commenterId"),
            @Mapping(target = "message", expression = "java(getCommentMessage(commentDTO))"),
            @Mapping(target = "respondentPicture", source = "commentDTO.commenterPicture"),
            @Mapping(target = "respondentId", source = "commentDTO.commenterId"),
            @Mapping(target = "formattedDate", source = "commentDTO.formattedDate"),
            @Mapping(target = "formattedTime", source = "commentDTO.formattedTime"),
            @Mapping(target = "notificationStatus", source = "commentDTO.notificationStatus"),

            @Mapping(target = "postId", source = "commentDTO.postId"),
            @Mapping(target = "commentId", source = "commentDTO.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(commentDTO))"),
    })
    public abstract CommentNotification toNotification(CommentDTO commentDTO);

    @Mappings(value = {
            @Mapping(target = "id", source = "replyDTO.id"),
            @Mapping(target = "receiverId", source = "replyDTO.replierId"),
            @Mapping(target = "message", expression = "java(getReplyMessage(replyDTO))"),
            @Mapping(target = "respondentPicture", source = "replyDTO.replierPicture"),
            @Mapping(target = "respondentId", source = "replyDTO.replierId"),
            @Mapping(target = "formattedDate", source = "replyDTO.formattedDate"),
            @Mapping(target = "formattedTime", source = "replyDTO.formattedTime"),
            @Mapping(target = "notificationStatus", source = "replyDTO.notificationStatus"),

            @Mapping(target = "postId", source = "replyDTO.postId"),
            @Mapping(target = "commentId", source = "replyDTO.commentId"),
            @Mapping(target = "replyId", source = "replyDTO.id"),

            @Mapping(target = "count", expression = "java(getNotificationCount(replyDTO))"),
    })
    public abstract ReplyNotification toNotification(ReplyDTO replyDTO);

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
            @Mapping(target = "count", defaultValue = "1")
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
            @Mapping(target = "count", defaultValue = "1")
    })
    public abstract CommentNotification toLikeNotification(CommentLike commentLike);

    @Mappings(value = {
            @Mapping(target = "id", source = "replyLike.id"),
            @Mapping(target = "message", expression = "java(getLikeMessage(replyLike))"),
            @Mapping(target = "respondentId", source = "replyLike.respondent.id"),
            @Mapping(target = "respondentPicture", source = "replyLike.respondent.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyLike.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyLike.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "replyLike.notificationStatus"),

            @Mapping(target = "receiverId", expression = "java(replyLike.getReceiverId())"),

            @Mapping(target = "postId", source = "replyLike.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyLike.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyLike.reply.id"),
            @Mapping(target = "count", defaultValue = "1")
    })
    public abstract ReplyNotification toLikeNotification(ReplyLike replyLike);

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
            @Mapping(target = "count", defaultValue = "1")
    })
    public abstract PostNotification toMentionNotification(PostMention postMention);

    @Mappings({
            @Mapping(target = "id", source = "commentMention.id"),
            @Mapping(target = "receiverId", expression = "java(commentMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(getMentionMessage(commentMention))"),
            @Mapping(target = "respondentId", source = "commentMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "commentMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(commentMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(commentMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "commentMention.notificationStatus"),

            @Mapping(target = "postId", source = "commentMention.comment.post.id"),
            @Mapping(target = "commentId", source = "commentMention.comment.id"),
            @Mapping(target = "count", defaultValue = "1")
    })
    public abstract CommentNotification toMentionNotification(CommentMention commentMention);

    @Mappings({
            @Mapping(target = "id", source = "replyMention.id"),
            @Mapping(target = "receiverId", expression = "java(replyMention.getReceiverId())"),
            @Mapping(target = "message", expression = "java(getMentionMessage(replyMention))"),
            @Mapping(target = "respondentId", source = "replyMention.mentioningUser.id"),
            @Mapping(target = "respondentPicture", source = "replyMention.mentioningUser.picture"),
            @Mapping(target = "formattedDate", expression = "java(Formatter.formatDate(replyMention.getCreatedAt()))"),
            @Mapping(target = "formattedTime", expression = "java(Formatter.formatTime(replyMention.getCreatedAt()))"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),

            @Mapping(target = "postId", source = "replyMention.reply.comment.post.id"),
            @Mapping(target = "commentId", source = "replyMention.reply.comment.id"),
            @Mapping(target = "replyId", source = "replyLike.reply.id"),
            @Mapping(target = "count", defaultValue = "1")

    })
    public abstract ReplyNotification toMentionNotification(ReplyMention replyMention);


    protected String getCommentMessage(CommentDTO commentDTO) {
        return commentDTO.getCommenterName() + " commented in your post: " + "\"" + commentDTO.getPostBody() + "\"";
    }

    protected String getReplyMessage(ReplyDTO replyDTO) {
        return replyDTO.getReplierName() + " replied to your comment: " + "\"" + replyDTO.getCommentBody() + "\"";
    }

    protected int getNotificationCount(CommentDTO commentDTO) {
        User author = userService.getById(commentDTO.getAuthorId());
        return commentService.getNotificationCountForRespondent(author, commentDTO.getPostId(), commentDTO.getCommenterId());
    }

    protected int getNotificationCount(ReplyDTO replyDTO) {
        User commenter = userService.getById(replyDTO.getCommenterId());
        return replyService.getNotificationCountForRespondent(commenter, replyDTO.getCommentId(), replyDTO.getReplierId());
    }
}


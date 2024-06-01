package com.elleined.socialmediaapi.mapper.notification.mention;

import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface MentionNotificationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(mention.getMentionedUser())"),
            @Mapping(target = "mention", expression = "java(mention)"),
            @Mapping(target = "post", expression = "java(post)")
    })
    PostMentionNotification toEntity(User creator, Mention mention, Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(mention.getMentionedUser())"),
            @Mapping(target = "mention", expression = "java(mention)"),
            @Mapping(target = "comment", expression = "java(comment)")
    })
    CommentMentionNotification toEntity(User creator, Mention mention, Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(mention.getMentionedUser())"),
            @Mapping(target = "mention", expression = "java(mention)"),
            @Mapping(target = "reply", expression = "java(reply)")
    })
    ReplyMentionNotification toEntity(User creator, Mention mention, Reply reply);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(mention.getMentionedUser())"),
            @Mapping(target = "mention", expression = "java(mention)"),
            @Mapping(target = "story", expression = "java(story)")
    })
    StoryMentionNotification toEntity(User creator, Mention mention, Story story);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(postMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "mentionId", source = "mention.id"),
            @Mapping(target = "postId", source = "post.id")
    })
    PostMentionNotificationDTO toDTO(PostMentionNotification postMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(commentMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "mentionId", source = "mention.id"),
            @Mapping(target = "commentId", source = "comment.id")
    })
    CommentMentionNotificationDTO toDTO(CommentMentionNotification commentMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "mentionId", source = "mention.id"),
            @Mapping(target = "replyId", source = "reply.id")
    })
    ReplyMentionNotificationDTO toDTO(ReplyMentionNotification replyMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(storyMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "mentionId", source = "mention.id"),
            @Mapping(target = "storyId", source = "story.id")
    })
    StoryMentionNotificationDTO toDTO(StoryMentionNotification storyMentionNotification);

}

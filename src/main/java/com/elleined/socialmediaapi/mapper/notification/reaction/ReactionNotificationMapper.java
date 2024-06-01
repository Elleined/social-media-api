package com.elleined.socialmediaapi.mapper.notification.reaction;

import com.elleined.socialmediaapi.dto.notification.reaction.CommentReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.PostReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.ReplyReactionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.reaction.StoryReactionNotificationDTO;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.PostReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.reaction.Reaction;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {Notification.Status.class})
public interface ReactionNotificationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(post.getCreator())"),
            @Mapping(target = "reaction", expression = "java(reaction)"),
            @Mapping(target = "post", expression = "java(post)")
    })
    PostReactionNotification toEntity(User creator, Post post, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(comment.getCreator())"),
            @Mapping(target = "reaction", expression = "java(reaction)"),
            @Mapping(target = "comment", expression = "java(comment)")
    })
    CommentReactionNotification toEntity(User creator, Comment comment, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(reply.getCreator())"),
            @Mapping(target = "reaction", expression = "java(reaction)"),
            @Mapping(target = "reply", expression = "java(reply)")
    })
    ReplyReactionNotification toEntity(User creator, Reply reply, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "receiver", expression = "java(story.getCreator())"),
            @Mapping(target = "reaction", expression = "java(reaction)"),
            @Mapping(target = "story", expression = "java(story)")
    })
    StoryReactionNotification toEntity(User creator, Story story, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(postReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "reactionId", source = "reaction.id"),
            @Mapping(target = "postId", source = "post.id")
    })
    PostReactionNotificationDTO toDTO(PostReactionNotification postReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(commentReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "reactionId", source = "reaction.id"),
            @Mapping(target = "commentId", source = "comment.id")
    })
    CommentReactionNotificationDTO toDTO(CommentReactionNotification commentReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "reactionId", source = "reaction.id"),
            @Mapping(target = "replyId", source = "reply.id")
    })
    ReplyReactionNotificationDTO toDTO(ReplyReactionNotification replyReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(storyReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "receiverId", source = "receiver.id"),
            @Mapping(target = "reactionId", source = "reaction.id"),
            @Mapping(target = "storyId", source = "story.id")
    })
    StoryReactionNotificationDTO toDTO(StoryReactionNotification storyReactionNotification);

}

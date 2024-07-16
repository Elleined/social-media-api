package com.elleined.socialmediaapi.mapper.notification.mention;

import com.elleined.socialmediaapi.dto.notification.mention.CommentMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.PostMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.ReplyMentionNotificationDTO;
import com.elleined.socialmediaapi.dto.notification.mention.StoryMentionNotificationDTO;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.mapper.mention.MentionMapper;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
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

@Mapper(
        componentModel = "spring",
        imports = {
                Notification.Status.class
        },
        uses = {
                UserMapper.class,
                MentionMapper.class,
                PostMapper.class,
                CommentMapper.class,
                ReplyMapper.class,
                StoryMapper.class,
                MentionMapper.class
        }
)
public interface MentionNotificationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "mention.mentionedUser"),
            @Mapping(target = "mention", source = "mention"),
            @Mapping(target = "post", source = "post")
    })
    PostMentionNotification toEntity(User creator, Mention mention, Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "mention.mentionedUser"),
            @Mapping(target = "mention", source = "mention"),
            @Mapping(target = "comment", source = "comment")
    })
    CommentMentionNotification toEntity(User creator, Mention mention, Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "mention.mentionedUser"),
            @Mapping(target = "mention", source = "mention"),
            @Mapping(target = "reply", source = "reply")
    })
    ReplyMentionNotification toEntity(User creator, Mention mention, Reply reply);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "mention.mentionedUser"),
            @Mapping(target = "mention", source = "mention"),
            @Mapping(target = "story", source = "story")
    })
    StoryMentionNotification toEntity(User creator, Mention mention, Story story);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(postMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "mentionDTO", source = "mention"),
            @Mapping(target = "postDTO", source = "post")
    })
    PostMentionNotificationDTO toDTO(PostMentionNotification postMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(commentMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "mentionDTO", source = "mention"),
            @Mapping(target = "commentDTO", source = "comment")
    })
    CommentMentionNotificationDTO toDTO(CommentMentionNotification commentMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "mentionDTO", source = "mention"),
            @Mapping(target = "replyDTO", source = "reply")
    })
    ReplyMentionNotificationDTO toDTO(ReplyMentionNotification replyMentionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(storyMentionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "mentionDTO", source = "mention"),
            @Mapping(target = "storyDTO", source = "story")
    })
    StoryMentionNotificationDTO toDTO(StoryMentionNotification storyMentionNotification);

}

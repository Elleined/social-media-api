package com.elleined.socialmediaapi.mapper.notification.reaction;

import com.elleined.socialmediaapi.dto.notification.reaction.*;
import com.elleined.socialmediaapi.mapper.main.CommentMapper;
import com.elleined.socialmediaapi.mapper.main.PostMapper;
import com.elleined.socialmediaapi.mapper.main.ReplyMapper;
import com.elleined.socialmediaapi.mapper.note.NoteMapper;
import com.elleined.socialmediaapi.mapper.react.ReactionMapper;
import com.elleined.socialmediaapi.mapper.story.StoryMapper;
import com.elleined.socialmediaapi.mapper.user.UserMapper;
import com.elleined.socialmediaapi.model.main.comment.Comment;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.main.reply.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.notification.reaction.*;
import com.elleined.socialmediaapi.model.reaction.Reaction;
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
                PostMapper.class,
                CommentMapper.class,
                ReplyMapper.class,
                NoteMapper.class,
                StoryMapper.class,
                ReactionMapper.class
        }
)
public interface ReactionNotificationMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "post.creator"),
            @Mapping(target = "reaction", source = "reaction"),
            @Mapping(target = "post", source = "post")
    })
    PostReactionNotification toEntity(User creator, Post post, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "comment.creator"),
            @Mapping(target = "reaction", source = "reaction"),
            @Mapping(target = "comment", source = "comment")
    })
    CommentReactionNotification toEntity(User creator, Comment comment, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "reply.creator"),
            @Mapping(target = "reaction", source = "reaction"),
            @Mapping(target = "reply", source = "reply")
    })
    ReplyReactionNotification toEntity(User creator, Reply reply, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "story.creator"),
            @Mapping(target = "reaction", source = "reaction"),
            @Mapping(target = "story", source = "story")
    })
    StoryReactionNotification toEntity(User creator, Story story, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "status", expression = "java(Status.UN_READ)"),
            @Mapping(target = "creator", source = "creator"),
            @Mapping(target = "receiver", source = "note.creator"),
            @Mapping(target = "reaction", source = "reaction"),
            @Mapping(target = "note", source = "note")
    })
    NoteReactionNotification toEntity(User creator, Note note, Reaction reaction);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(postReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "reactionDTO", source = "reaction"),
            @Mapping(target = "postDTO", source = "post")
    })
    PostReactionNotificationDTO toDTO(PostReactionNotification postReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(commentReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "reactionDTO", source = "reaction"),
            @Mapping(target = "commentDTO", source = "comment")
    })
    CommentReactionNotificationDTO toDTO(CommentReactionNotification commentReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(replyReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "reactionDTO", source = "reaction"),
            @Mapping(target = "replyDTO", source = "reply")
    })
    ReplyReactionNotificationDTO toDTO(ReplyReactionNotification replyReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(storyReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "reactionDTO", source = "reaction"),
            @Mapping(target = "storyDTO", source = "story")
    })
    StoryReactionNotificationDTO toDTO(StoryReactionNotification storyReactionNotification);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "message", expression = "java(noteReactionNotification.getMessage())"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "creatorDTO", source = "creator"),
            @Mapping(target = "receiverDTO", source = "receiver"),
            @Mapping(target = "reactionDTO", source = "reaction"),
            @Mapping(target = "noteDTO", source = "note")
    })
    NoteReactionNotificationDTO toDTO(NoteReactionNotification noteReactionNotification);
}

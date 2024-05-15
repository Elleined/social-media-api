package com.elleined.socialmediaapi.mapper.main;

import com.elleined.socialmediaapi.dto.main.PostDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.NotificationStatus;
import com.elleined.socialmediaapi.model.hashtag.HashTag;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Status;
import com.elleined.socialmediaapi.model.mention.Mention;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Post.CommentSectionStatus.class, Status.class})
public interface PostMapper extends CustomMapper<Post, PostDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "body", source = "body"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "attachedPicture", source = "attachedPicture"),
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "notificationStatus", source = "notificationStatus"),
            @Mapping(target = "commentSectionStatus", source = "commentSectionStatus"),
            @Mapping(target = "pinnedCommentId", source = "pinnedComment.id"),
            @Mapping(target = "hashTagIds", expression = "java(post.getAllHashTagIds())"),
            @Mapping(target = "mentionIds", expression = "java(post.getAllMentionIds())"),
            @Mapping(target = "reactionIds", expression = "java(post.getAllReactionIds())"),
            @Mapping(target = "commentIds", expression = "java(post.getAllCommentIds())"),
            @Mapping(target = "savingUserIds", expression = "java(post.getAllSavingUserIds())"),
            @Mapping(target = "sharerIds", expression = "java(post.getAllSharerIds())")
    })
    PostDTO toDTO(Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "body", expression = "java(body)"),
            @Mapping(target = "status", expression = "java(Status.ACTIVE)"),
            @Mapping(target = "attachedPicture", expression = "java(attachedPicture)"),
            @Mapping(target = "creator", expression = "java(creator)"),
            @Mapping(target = "notificationStatus", expression = "java(notificationStatus)"),
            @Mapping(target = "commentSectionStatus", expression = "java(CommentSectionStatus.OPEN)"),
            @Mapping(target = "pinnedComment", expression = "java(null)"),
            @Mapping(target = "hashTags", expression = "java(hashTags)"),
            @Mapping(target = "mentions", expression = "java(mentions)"),
            @Mapping(target = "reactions", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "savingUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "sharers", expression = "java(new java.util.HashSet<>())")
    })
    Post toEntity(User creator,
                  String body,
                  String attachedPicture,
                  Set<HashTag> hashTags,
                  Set<Mention> mentions,
                  @Context NotificationStatus notificationStatus);
}

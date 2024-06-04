package com.elleined.socialmediaapi.mapper.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends CustomMapper<User, UserDTO> {

    @Override
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "picture", source = "picture"),
            @Mapping(target = "UUID", source = "UUID"),
            @Mapping(target = "noteId", source = "note.id"),
            @Mapping(target = "storyId", source = "story.id"),
            @Mapping(target = "sentFriendRequestIds", expression = "java(user.getAllSentFriendRequestIds())"),
            @Mapping(target = "receiveFriendRequestIds", expression = "java(user.getAllReceiveFriendRequestIds())"),
            @Mapping(target = "postIds", expression = "java(user.getAllPostIds())"),
            @Mapping(target = "commentIds", expression = "java(user.getAllCommentIds())"),
            @Mapping(target = "replyIds", expression = "java(user.getAllReplyIds())"),
            @Mapping(target = "reactionIds", expression = "java(user.getAllReactionIds())"),
            @Mapping(target = "savedPostIds", expression = "java(user.getAllSavedPostIds())"),
            @Mapping(target = "votedCommentIds", expression = "java(user.getAllVotedCommentIds())"),
            @Mapping(target = "sharedPostIds", expression = "java(user.getAllSharedPostIds())"),
            @Mapping(target = "blockedUserIds", expression = "java(user.getAllBlockedUserIds())"),
            @Mapping(target = "friendIds", expression = "java(user.getAllFriendIds())"),
            @Mapping(target = "followerIds", expression = "java(user.getAllFollowerIds())"),
            @Mapping(target = "followingIds", expression = "java(user.getAllFollowingIds())"),
    })
    UserDTO toDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "name", expression = "java(name)"),
            @Mapping(target = "email", expression = "java(email)"),
            @Mapping(target = "picture", expression = "java(picture)"),
            @Mapping(target = "UUID", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "note", expression = "java(null)"),
            @Mapping(target = "story", expression = "java(null)"),
            @Mapping(target = "sentFriendRequests", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "receiveFriendRequests", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "posts", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "replies", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "reactions", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "savedPosts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "votedComments", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "sharedPosts", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "blockedUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "friends", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "followers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "followings", expression = "java(new java.util.HashSet<>())"),

            // Notifications
            @Mapping(target = "commentNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "replyNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "postMentionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "commentMentionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "replyMentionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "storyMentionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "postReactionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "commentReactionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "replyReactionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "storyReactionNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "followerNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "sharedPostNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "voteNotifications", expression = "java(new java.util.ArrayList<>())"),
            @Mapping(target = "friendRequestNotifications", expression = "java(new java.util.ArrayList<>())")
    })
    User toEntity(String name,
                  String email,
                  @Context String picture);
}

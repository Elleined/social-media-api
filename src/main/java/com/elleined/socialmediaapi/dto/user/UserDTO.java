package com.elleined.socialmediaapi.dto.user;

import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.PostReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends DTO {
    private String name;
    private String email;
    private String picture;
    private String UUID;
    private int noteId;
    private int storyId;
    private Set<Integer> sentFriendRequestIds;
    private Set<Integer> receiveFriendRequestIds;
    private List<Integer> postIds;
    private List<Integer> commentIds;
    private List<Integer> replyIds;
    private Set<Integer> savedPostIds;
    private Set<Integer> votedCommentIds;
    private Set<Integer> sharedPostIds;
    private Set<Integer> blockedUserIds;
    private Set<Integer> friendIds;
    private Set<Integer> followerIds;
    private Set<Integer> followingIds;
    private List<Integer> reactionIds;
}

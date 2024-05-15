package com.elleined.socialmediaapi.dto.user;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.dto.DTO;
import com.elleined.socialmediaapi.model.main.Comment;
import com.elleined.socialmediaapi.model.main.Post;
import com.elleined.socialmediaapi.model.main.Reply;
import com.elleined.socialmediaapi.model.note.Note;
import com.elleined.socialmediaapi.model.story.Story;
import com.elleined.socialmediaapi.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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

    @Builder
    public UserDTO(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String name,
                   String email,
                   String picture,
                   String UUID,
                   int noteId,
                   int storyId,
                   Set<Integer> sentFriendRequestIds,
                   Set<Integer> receiveFriendRequestIds,
                   List<Integer> postIds,
                   List<Integer> commentIds,
                   List<Integer> replyIds,
                   Set<Integer> savedPostIds,
                   Set<Integer> votedCommentIds,
                   Set<Integer> sharedPostIds,
                   Set<Integer> blockedUserIds,
                   Set<Integer> friendIds,
                   Set<Integer> followerIds,
                   Set<Integer> followingIds) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.UUID = UUID;
        this.noteId = noteId;
        this.storyId = storyId;
        this.sentFriendRequestIds = sentFriendRequestIds;
        this.receiveFriendRequestIds = receiveFriendRequestIds;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
        this.savedPostIds = savedPostIds;
        this.votedCommentIds = votedCommentIds;
        this.sharedPostIds = sharedPostIds;
        this.blockedUserIds = blockedUserIds;
        this.friendIds = friendIds;
        this.followerIds = followerIds;
        this.followingIds = followingIds;
    }
}

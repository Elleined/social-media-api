package com.elleined.socialmediaapi.newdto.user;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.newdto.DTO;
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
    private Set<Integer> votedCommentIds;
    private Set<Integer> blockedUserIds;
    private Set<Integer> sharedPostIds;
    private Set<Integer> savedPostIds;
    private Set<Integer> followerIds;
    private Set<Integer> followingIds;
    private Set<Integer> friendIds;
    private Set<FriendRequest> sentFriendRequestIds;
    private Set<FriendRequest> receiveFriendRequestIds;
    private List<Integer> postIds;
    private List<Integer> commentIds;
    private List<Integer> replyIds;
    private int noteId;

    @Builder
    public UserDTO(int id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   String name,
                   String email,
                   String picture,
                   String UUID,
                   Set<Integer> votedCommentIds,
                   Set<Integer> blockedUserIds,
                   Set<Integer> sharedPostIds,
                   Set<Integer> savedPostIds,
                   Set<Integer> followerIds,
                   Set<Integer> followingIds,
                   Set<Integer> friendIds,
                   Set<FriendRequest> sentFriendRequestIds,
                   Set<FriendRequest> receiveFriendRequestIds,
                   List<Integer> postIds,
                   List<Integer> commentIds,
                   List<Integer> replyIds,
                   int noteId) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.UUID = UUID;
        this.votedCommentIds = votedCommentIds;
        this.blockedUserIds = blockedUserIds;
        this.sharedPostIds = sharedPostIds;
        this.savedPostIds = savedPostIds;
        this.followerIds = followerIds;
        this.followingIds = followingIds;
        this.friendIds = friendIds;
        this.sentFriendRequestIds = sentFriendRequestIds;
        this.receiveFriendRequestIds = receiveFriendRequestIds;
        this.postIds = postIds;
        this.commentIds = commentIds;
        this.replyIds = replyIds;
        this.noteId = noteId;
    }
}

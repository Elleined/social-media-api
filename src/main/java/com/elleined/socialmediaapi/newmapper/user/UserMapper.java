package com.elleined.socialmediaapi.newmapper.user;

import com.elleined.socialmediaapi.dto.user.UserDTO;
import com.elleined.socialmediaapi.model.user.User;
import com.elleined.socialmediaapi.newmapper.CustomMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends CustomMapper<User, UserDTO> {
    @Override
    @Mappings({
            int id,
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
            int noteId,
            int storyId,
    })
    UserDTO toDTO(User user);
}

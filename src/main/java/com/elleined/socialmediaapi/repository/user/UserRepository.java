package com.elleined.socialmediaapi.repository.user;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name LIKE CONCAT('%', :name, '%')")
    Page<User> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT u.blockedUsers FROM User u WHERE u = :currentUser")
    Page<User> findAllBlockedUsers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followers FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followings FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowings(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.friends FROM User u WHERE u = :currentUser")
    Page<User> findAllFriends(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.receiveFriendRequests FROM User u WHERE u = :currentUser")
    Page<FriendRequest> findAllFriendRequests(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.savedPosts FROM User u WHERE u = :currentUser")
    Page<Post> findAllSavedPosts(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.sharedPosts FROM User u WHERE u = :currentUser")
    Page<Post> findAllSharedPosts(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.commentNotifications FROM User u WHERE u = :currentUser")
    Page<CommentNotification> findAllReceiveCommentNotifications(User currentUser, Pageable pageable);

    @Query("SELECT u.replyNotifications FROM User u WHERE u = :currentUser")
    Page<ReplyNotification> findAllReceiveReplyNotifications(User currentUser, Pageable pageable);
}